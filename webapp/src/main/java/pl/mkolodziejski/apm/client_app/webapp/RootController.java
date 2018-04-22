package pl.mkolodziejski.apm.client_app.webapp;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/")
public class RootController {

    @RequestMapping(method = RequestMethod.GET)
    public String index() {
        StringBuilder sb = new StringBuilder();
        sb.append("<html>");
        sb.append(" <body>");
        sb.append("     API URLs:");
        sb.append("     <ul>");
        sb.append("         <li><a href='/efficient/nextId'>/efficient/nextId</a></li>");
        sb.append("         <li><a href='/delayed/nextId'>/delayed/nextId</a></li>");
        sb.append("         <li><a href='/degrading/nextId'>/degrading/nextId</a></li>");
        sb.append("         <li><a href='/resource_intensive/is_prime/100000000'>/resource_intensive/is_prime/100000000</a></li>");
        sb.append("         <li><a href='/resource_intensive/gc'>/resource_intensive/gc</a></li>");
        sb.append("     </ul>");
        sb.append(" </body>");
        sb.append("</html>");
        return sb.toString();
    }
}
