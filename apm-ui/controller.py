from app import app
from config import *
from flask import render_template, abort, jsonify
import os.path


@app.route('/')
def index():
    return render_template('index.html')



@app.route('/csv/<module>/<metric>')
def csv(module=None, metric=None):
    if module is None or metric is None:
        abort(400)

    filePath = '{}/{}/{}.csv'.format(LOGS_DIR, module, metric)
    if not os.path.exists(filePath):
        abort(404)

    file = open(filePath, "r")
    content = file.read()
    file.close()

    response = app.response_class(
        response = content,
        status = 200,
        mimetype = 'text/csv'
    )

    return response



@app.route('/get_method_usages')
def method_usages():
    path = "{}/runtime".format(LOGS_DIR)
    methods = []
    if os.path.isdir(path):
        methods = [f[:-4] for f in os.listdir(path)]
    return jsonify(methods)
