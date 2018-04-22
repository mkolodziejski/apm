from flask import Flask
from config import *


app = Flask(__name__)
app.config['TEMPLATES_AUTO_RELOAD'] = IS_DEBUG

from controller import *


if __name__ == '__main__':
    app.run(host='0.0.0.0', threaded=True)