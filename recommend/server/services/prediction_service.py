from keras.models import load_model
import pandas as pd
import numpy as np
from datetime import datetime

class PredictionService:
    def __init__(self, prediction, users, products):
        self.prediction = prediction
        self.users = users
        self.products = products
    
    def getAllModelInfo(self):
        infomation = self.prediction.find_one({'_id': 'prediction-infomation'})
        if infomation == None:
            return []
        else:
            return list(infomation['versions'])
    
    def predict(self, userId: str, offset: int, limit: int):
        prediction = self.prediction.find_one({'user_id': userId})
        recommend_products = []
        if prediction == None:
            recommend_products = self.modelPredict(userId)
            self.prediction.insert_one({'_id': user_id, 'user_id': user_id, 'recommend': recommend_products})
        elif len(prediction['recommend']) == 0:
            recommend_products = self.modelPredict(userId)
            self.prediction.update_one({'user_id': userId}, {'$set': {'recommend': recommend_products}}, upsert=True)
        else:
            recommend_products = prediction['recommend']

        if len(recommend_products) == 0:
            recommend_products = list(self.products.find({}))
        
        length = len(recommend_products)
        products = []
        if length == 0 or offset >= length:
            products = []
        elif offset+limit >= length:
            products = recommend_products[offset:]
        else :
            products = recommend_products[offset, offset+limit]
        return {
            "total": len(products),
            "products": products
        }

    def modelPredict(self, userId: str):
        try:
            model = load_model('../model.keras')
            products = getProducts()
            users = getUserInfo(userId, len(products))
            predict_ratings = model.predict([
                [
                    np.array(users['_id']),
                    np.array(products['_id'])
                ],
                np.array([np.array(val) for val in users['user_info']]),
                np.array([np.array(val) for val in products['product_info']])
            ])
            recommend_products = np.array([array[0] for array in predict_ratings])[::-1].argsort()
            product_map = {}
            for i in len(products):
                product_map[products.iloc[i]['_id']] = products.iloc[i]['product_id']

            return mapProduct(recommend_products, product_map)
        except:
            return []

    def mapProduct(self, recommend_products, product_map):
        def f(index):
            return product_map[index]
        return list(map(f, recommend_products))
    
    def getUsers(self, userId: str, size: int):
        user = users.find_one({'user_id': userId})
        age = datetime.now().year - user['dob'].year
        gender = 0
        if user['gender'] == 'MALE':
            gender = 1
        return pd.DataFrame.from_records([{'_id': user._id, 'user_info': [gender,age]}] * size)
    
    def getProducts(self):
        products = pd.DataFrame(list(self.products.find()))
        # get all categories
        categories = set()
        for product_caterories in products['categories']:
            categories.update(product_caterories)
        categories = list(categories)
        category_index_map = {category_id:index for index, category_id in enumerate(categories)}
        product_feature = np.zeros((len(products), len(categories)))
        result = pb.DataFrame()
        for i in range(len(products)):
            # điền thể loại cho sản phẩm thứ i
            for category in products.iloc[i]['categories']:
                category_index = category_index_map[category]
                product_feature[i, category_index] = 1
            result._append({'_id': products.iloc[i]['_id'], 'product_id': products.iloc[i]['product_id'], 'product_info': product_feature[i]}, ignore_index = True)
        return result
    
        
        

