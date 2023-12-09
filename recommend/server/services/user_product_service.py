from flask import jsonify
from bson.json_util import dumps
import datetime
import requests

class UserProductService:
    def __init__(self, user_product):
        self.user_product = user_product

    def upsertUserProduct(self, body):
        data = {
            'user_product_id': body['userProductId'],
            'user_id': body['userId'],
            'product_id': body['productId'],
            'rating': float(body['rating'])
        }
        self.user_product.update_one({'_id' : data['user_product_id']}, { '$set': data }, upsert=True)
        return data
    
    def getAllUserProduct(self, isRefresh=False):
        return list(self.user_product.find({}))