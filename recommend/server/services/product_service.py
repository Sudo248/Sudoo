import datetime
from bson.json_util import dumps

class ProductService:
    def __init__(self, counter, products):
        self.counter = counter
        self.products = products
    

    def upsertProduct(self, body):
        product = {
            'product_id': body['productId'],
            'categories': body['categories']
        }
        _id = self.products.find_one({'product_id': product['product_id']}, {'_id': 1})
        if _id == None:
            productCounter = self.counter.find_one({'collection': 'product'}, {'counter': 1})
            if productCounter == None:
                productCounter = 0
                self.counter.insert_one({'collection': 'product', 'counter': 0})
            else:
                productCounter = int(productCounter['counter']) + 1
                self.counter.update_one({'collection': 'product'}, {'$set': {'counter': productCounter}})
            self.products.update_one({'_id' : int(productCounter)}, { '$set': product }, upsert=True)
        else:
            self.products.update_one({'_id' : int(_id)}, { '$set': product }, upsert=True)
        return product
    
    def getProducts(self):
        return list(self.products.find({}))