import os
import requests
from flask import Flask, request
from pymongo.mongo_client import MongoClient
from pymongo.server_api import ServerApi
from services.user_product_service import UserProductService
from services.user_service import UserService
from services.product_service import ProductService
from services.prediction_service import PredictionService

app = Flask(__name__)

uriMongoDb = "mongodb+srv://sudo248dev:sudo24082001@cluster0.k1a99sp.mongodb.net/?retryWrites=true&w=majority"

mongoClient = MongoClient(uriMongoDb, server_api=ServerApi('1'))
db = mongoClient.sudoo
counter = db.counter

userProductService = UserProductService(db.user_product)
userService = UserService(counter, db.users)
productService = ProductService(counter, db.products)
predictionService = PredictionService(db.prediction, db.users, db.products)

@app.route('/api/v1/user-product', methods=['POST', 'GET'])
def userProductController():
    if request.method == 'POST':
        return userProductService.upsertUserProduct(request.get_json())
    else:
        return userProductService.getAllUserProduct()


@app.route('/api/v1/users', methods=['POST', 'GET'])
def productController():
    if request.method == 'POST':
        body = request.get_json()
        return userService.upsertUser(body)
    else:
        return userService.getUsers()


@app.route('/api/v1/products', methods=['POST', 'GET'])
def userController():
    if request.method == 'POST':
        return productService.upsertProduct(request.get_json())
    else:
        return productService.getProducts()

@app.route('/api/v1/predictions/<userId>', methods=['GET'])
def getPredictions(userId):
    offset = int(request.args.get('offset'))
    limit = int(request.args.get('limit'))
    return predictionService.predict(userId, offset, limit)

@app.route('/api/v1/update-model', methods=['POST'])
def downloadLatestModel():
    body = request.get_json()
    response = requests.get(body['download_model_url'])
    open('model.keras', 'wb').write(response.content)
    return f"Update model {body['model_name']} successful!!!"

@app.route('/api/v1/recommend/versions', methods=['GET'])
def getAllModelInfo():
    try:
        return {
            'success': True,
            'message': 'Success',
            'data': predictionService.getAllModelInfo()
        }
    except:
        return {
            'success': False,
            'message': 'Something went wrong',
            'data': null
        }

if __name__ == '__main__':
    app.run(debug=True)