# APM prototype

This is an APM solution **prototype**. It allows to monitor the most important metrics in Java applications run on Linux OS.


## Build and run

The simpliest way to build and run the project is to use Docker. You can do it with a single command:

`./build-run.sh`

Then go to:
* [http://localhost:8080](http://localhost:8080) to invoke example REST API
* [http://localhost:5000](http://localhost:5000) to open Metrics UI


The `build-run.sh` script does the following:
+ builds webapp
+ builds java agent
+ builds Docker image
  + installs all dependencies
  + copies Java agent, metrics UI and example webapp
  + sets up entrypoint
+ runs Docker image
  + runs metrics UI Python app
  + runs Spring Boot app with Java agent

Alternatively, you can do all that manually, but why bother...?


### Java agent configuration

There's a set of configuration options you may use to change the default behavior of Java agent:

| name | default value | description |
| --- | --- | --- |
| `interval` | 60 | interval between subsequent metrics collection, in seconds |
| `logPath` | /apm/logs | directory to which CSVs are saved |
| `instrumentationPackage` | pl.mkolodziejski.apm.client_app | only classes located in this package (and subpackages) will be instrumented |
| `instrumentationAnnotatonClass` | org.springframework.web.bind.annotation.RequestMapping | only methods annotatated with this class will be instrumented |
| `debugOn` | true |  |

Multiple options should be comma-separated. Example:

`java -javaagent:/apm/javaagent/javaagent-1.0.0.jar=interval=10,debugOn=false -jar /webapp/webapp-1.0.0.jar`

To set this options for Docker image, edit the `entrypoint.sh` file and re-run `build-run.sh`.


## Project description

The project constists of three parts:
* [Java agent](#java-agent)
* [metrics UI](#metrics-ui)
* [an example REST API to be monitored](#an-example-rest-api-to-be-monitored)

They are briefly described below.


### Java agent

This is a core part of this APM solution. It collects all the data from the JVM, OS and application. The following metrics are gathered:
+ JVM
  + heap memory: init, used, committed and max
  + threads: count, peak count
  + GC: count and time consumed
+ OS (Linux only)
  + CPU load
  + memory: total, available, free, swap total, swap free
  + network (for each interface): bytes received, bytes sent
+ application
  + time and number of executions of each method annotated with annotation specified in configuration (Spring's `@RequestMapping` by default)

All the data is periodically collected and serialized into CSV files.

JVM metrics are read from JMX MXBeans exposed by JVM.

Operating system metrics come from standard Linux `procfs` files related to system information:
* `/proc/stat` to read CPU usage stats
* `/proc/meminfo` to get memory-related information
* `/proc/net/dev` to gather network interfaces usage

The code measuring time of execution of each annotated method is being injected to the bytecode at runtime. It is done with [ASM library](http://asm.ow2.org/) through Java instrumentation API.


### Metrics UI

It is a Python application which exposes web interface. It is a bridge to fetch CSVs dumped by Java agent, plus simply serves static HTML, JS and CSS files.

#### Important! License note

This UI uses [HighCharts](https://www.highcharts.com/) library to present the data visually. While this APM code is licensed under GNU GPL, HighCharts is a proprietary software which is free only for personal use! Please visit their site to check the terms of licensing and pricing.


### An example REST API to be monitored

This is a Spring Boot application which imitates REST interface with a couple of different time and resources usage characteristics.
* `/efficient/nextId` is a service with near-zero constant execution time
* `/delayed/nextId` is a service with non-zero constant execution time
* `/degrading/nextId` is a service whose execution time goes up with every execution
* `/resource_intensive/is_prime/{number}` is a service consuming lots of CPU and memory (for sufficiently big numbers)
* `/resource_intensive/gc` is a simple invocation of `System.gc()` which allows to observe GC freeing memory




## Prototype simplifications, limitations and constraints

This piece of software is by no means production-ready. It is just a prototype. It suffers from several limitations:
+ Architectural simplifications
  + It is a one-host solution. The monitored and monitoring machines are not separate, although they definitely should be.
  + CSV file is not a flexible storage for such data. The collected data should be stored in some kind of queryable database.
  + Operating system metrics should be collected by an external agent, separately from Java process. Now, if Java process dies, we lose OS metrics, which is unacceptable on production environments. Additionally, if more than one monitored Java process runs on the same machine, we have OS metrics duplicated.
+ Java agent limitations
  + Method execution time collector does not differentiate overloaded methods
  + Injected method execution time code does not handle situations when an exception is thrown from outside of the method&apos;s code (for example from a method invoked from the monitored method)
+ Metrics UI constraints
  + GC algorithms presented on charts are hardcoded
  + Network interface (eth0) presented on charts is hardcoded

