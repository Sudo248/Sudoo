from keras.models import load_model
import pandas as pd
import numpy as np
from datetime import datetime

class PredictionService:
    def __init__(self, prediction, users, products):
        self.prediction = prediction
        self.users = users
        self.products = products
    
    def predict(self, userId: str, offset: int, limit: int):

        return "123"

    def modelPredict(self, userId: str):
        model = load_model('../model.keras')
        userInfo = getUserInfo(userId)

    
    def getUserInfo(self, userId):
        user = users.find_one({'user_id': userId})
        age = datetime.now().year - user['dob'].year
        gender = 0
        if user['gender'] == 'MALE':
            gender = 1
        return [gender, age]
    
    def getProductInfo(self):
        products = pd.DataFrame(list(self.products.find()))
        # get all categories
        categories = set()
        for product_caterories in products['categories']:
            categories.update(product_caterories)
        categories = list(categories)
        category_index_map = {category_id:index for index, category_id in enumerate(categories)}
        product_feature = np.zeros((len(products), len(categories)))
        product_category_map = {}
        for i in range(len(products)):
            # điền thể loại cho sản phẩm thứ i
            for category in products.iloc[i]['categories']:
                category_index = category_index_map[category]
                product_feature[i, category_index] = 1
            k = {products.iloc[i]['product_id']: product_feature[i]}
            product_category_map.update(k)
        

