# -*- coding: utf-8 -*-
"""Sudoo_Neural_Collaborative_filtering_final.ipynb

Automatically generated by Colaboratory.

Original file is located at
    https://colab.research.google.com/drive/1s2gLhP005r8qps5doclhvvQuX1_oV_Vc
"""

import os
import re
import numpy as np
import pandas as pd
import tensorflow as tf
from sklearn.model_selection import train_test_split

"""Load Data from mongodb"""

# connect mongodb
import pymongo
from pymongo.mongo_client import MongoClient
from pymongo.server_api import ServerApi

# uri (uniform resource identifier) defines the connection parameters
uri = 'mongodb+srv://admin:admin123@cluster0.k1a99sp.mongodb.net/?retryWrites=true&w=majority'
# start client to connect to MongoDB server
client = MongoClient( uri )
sudoo = client.sudoo

# load all user-product data to dataframe
users = pd.DataFrame(list(sudoo.users.find()))
products = pd.DataFrame(list(sudoo.products.find()))
ratings = pd.DataFrame(list(sudoo.user_product.find()))

"""PREPROCESSING"""

# map gender to int
gender_map = {'FEMALE':0, 'MALE':1, 'OTHER': 2}
users['gender'] = users['gender'].map(gender_map)

# calculate age
now = pd.Timestamp.now()
users['dob'] = users['dob'].apply(lambda dob: now.year - dob.year)
users.rename(columns = {'dob':'age'}, inplace = True)

# create user_info
users['user_info'] = users.apply(lambda row: list([row['gender'], row['age']]), axis=1)
# lưu user theo user_id và vector thông tin của user đó ex 1: [0,10,1]
user_map = {}
for i in range(len(users['user_id'])):
    k = {users.iloc[i]['user_id']: np.array(users.iloc[i]['user_info'])}
    user_map.update(k)

# thêm cột user_inf trong bảng rattings với giá trị là user_id
ratings['user_info'] = ratings.loc[:, 'user_id']
# map các giá trị user_id trong cột user_info với user_map để lấy dc vector user_info
ratings['user_info'] = ratings['user_info'].map(user_map)

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

# thêm cột product_info với giá trị mỗi hàng là product_id
ratings['product_info'] = ratings.loc[:, 'product_id']
products['product_info'] = products.loc[:,'product_id']
# map từng sản phẩm với onehot vector thể loại.
ratings['product_info'] = ratings['product_info'].map(product_category_map)
products['product_info'] = products['product_info'].map(product_category_map)

# create user index map
user_index_map = {}
for i in range(len(users)):
  k = {users.iloc[i]['user_id']: users.iloc[i]['_id']}
  user_index_map.update(k)

# create product index map
product_index_map = {}
for i in range(len(products)):
  k = {products.iloc[i]['product_id']: products.iloc[i]['_id']}
  product_index_map.update(k)

# map user and product index to ratings
ratings['user_id'] = ratings['user_id'].map(user_index_map)
ratings['product_id'] = ratings['product_id'].map(product_index_map)

train, test = train_test_split(ratings, test_size= 0.05, random_state= 42)

# get total product size and user size
product_size = len(products['product_id'].unique())
user_size = len(users['user_id'].unique())
category_size = len(categories)

from sklearn.model_selection import train_test_split
from keras.models import Model
from keras.layers import Input, Embedding, Flatten, Dense, Concatenate
import tensorflow as tf

#Creating product embedding path
product_input = Input(shape = (1,), name = "Product-Input")
product_embedding = Embedding(input_dim=product_size + 1, output_dim=10, name= "Product-Embedding")(product_input)
product_vec = Flatten(name = "Flatten-Product")(product_embedding)

#Creating user embedding path
user_input = Input(shape = (1,), name = "User-Input")
user_embedding = Embedding(input_dim=user_size + 1, output_dim=10, name = "User-Embedding")(user_input)
user_vec = Flatten(name = "Flatten-Users")(user_embedding)

#creating product_info embedding path
category_input = Input(shape = (category_size,), name = "Category-Input")
category_embedding = Embedding(input_dim=category_size, output_dim=5, name = "Category-Embedding")(category_input)
category_vec = Flatten(name = "Flatten-Category")(category_embedding)


# Creating user_info path
user_info_input = Input(shape = (2,), name = "User-Info-Input")
user_info_vec = Flatten(name = "Flatten-User-Info")(user_info_input)

#Concatenate features
conc = Concatenate()([product_vec, user_vec, user_info_vec, category_vec])

#Weight Initialization
initializer = tf.keras.initializers.RandomUniform(minval=0., maxval=1., seed = None)

#Add fully-connected-layers
fc1 = Dense(128, activation='relu')(conc)
fc2 = Dense(100, activation='relu')(fc1)
fc3 = Dense(80, activation='relu')(fc2)
fc4 = Dense(60, activation='relu')(fc3)
fc5 = Dense(32, activation='relu')(fc4)
out = Dense(1)(fc5)

#Create model and compile it
model = Model([[user_input, product_input], user_info_input, category_input], out)
model.compile(optimizer='adam', loss='mean_absolute_error') #mean_absolute_error   mean_squared_error

history = model.fit(
    [
        [train.user_id.values, train.product_id.values],  # Input cho người dùng và sản phẩm
        np.array([np.array(val) for val in train.user_info]),  # Input cho thông tin người dùng
        np.array([np.array(val) for val in train.product_info])  # Input cho thông tin sản phẩm
    ],
    train.rating,  # Đầu ra (rating)
    epochs=18,  # Số lần lặp qua toàn bộ tập dữ liệu
    verbose=0  # Hiển thị quá trình đào tạo (1: hiển thị, 0: không hiển thị)
)

# test model
evaluate = model.evaluate(
    [
        [test.user_id, test.product_id],
        np.array([np.array(val) for val in test.user_info]),
        np.array([np.array(val) for val in test.product_info])
    ],
    test.rating
)

# get prediction infomation

def getNextVersion(currentVersion: str) -> str:
    versionNumbers = [int(i) for i in currentVersion.split('.')]
    if versionNumbers[2] >= 9:
        versionNumbers[1] += 1
    else:
        versionNumbers[2] += 1
    if versionNumbers[1] >= 9:
        versionNumbers[0] += 1
    return '.'.join(map(str,versionNumbers))

from datetime import datetime
prediction = sudoo.prediction
_id = 'prediction-infomation'
infomation = prediction.find_one({'_id': _id})
currentVersion = '0.0.0'
nextVersion = '0.0.0'
if infomation == None:
    infomation = {
        'storageUrl': 'https://storage.googleapis.com/sudoo-buckets/models',
        'recommendServiceUrl': 'http://recommend-service:5000/api/v1/update-model',
        'versions': [
            {
                'version': currentVersion,
                'evaluate': evaluate,
                'build_at': datetime.now(),
                'user_size': user_size,
                'product_size': product_size,
                'category_size': category_size,
                'rating_size': len(ratings)
            }
        ]
    }
else:
    versions = list(infomation['versions'])
    currentVersion = versions[-1]['version']
    nextVersion = getNextVersion(currentVersion)
    versions.append({
        'version': nextVersion,
        'evaluate': evaluate,
        'build_at': datetime.now(),
        'user_size': user_size,
        'product_size': product_size,
        'category_size': category_size,
        'rating_size': len(ratings)
    })
    infomation['versions'] = versions

prediction.update_one({'_id': _id}, {'$set': infomation}, upsert=True)

# save model
model_name = f'Sudoo_Neural_Collaborative_filtering_{nextVersion}.keras'
model.save(model_name)

# upload to google storage
from gcloud import storage
from oauth2client.service_account import ServiceAccountCredentials

credentials_dict = {
    'type': 'service_account',
    'client_id': '102834532880102294285',
    'client_email': '136779879298-compute@developer.gserviceaccount.com',
    'private_key_id': '5b283a7423847ec591c699df3d5956d5fd373c14',
    'private_key': '''-----BEGIN PRIVATE KEY-----
MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCe50qYmhEPcyhu
gd+WabWerro7hatnGPbFEfeDB7N7INZG7I2sm6geDCSvQmblJFcGa8JoK+ItcQ6Y
Pctkf2qVww5KXZ52NYAqjdoKQqOnn+NMPPFzr/NCTfm2udxQWd4lw/Q2yf57y3MW
cyorWftek93AjrUCSRgHb17vwXN1FghEE3PsbxPyjlKyDobnomnskFp4zIREyZIp
TO1FLSmYJiRksQSnS1Df07BcOv5VEVn1t7+GjB0lWsaM2qWne9DJv9CC3+LXIQtu
ZZkrAzxKdqEdTxZ3+lOHLquRPu9RAizwP5hOg5iZ9EQyPFLaypLlAfZntl4rQh4K
n2KR1EtDAgMBAAECggEAGT+HMZvFRdmr3RyDV2XA0bpR5UmWFExkpVJ3VplU0iXD
tkLkOJQGUI6TDkqJiTN5emUz32kdnSUfcbQxNZeDQCbdMJa91MAnzFsmqnEFNN9U
S9WhHcXMFpxyJtpS9cIZvebBTn/7kKr2bzo+7eLJwdt9JW6SDI9q+H911mLmrVM3
CgRt3gGZMNroPuQN7/BArHCbBFXvtcx3pv5IXvd2F0gIwWtd3EwEJGJd5BBp7J2c
H+fGAB4UP1bA4ee3M7LnpzX6ZXViKlUf/G4sHnkmYJszTQGDxvgeOofxD+LI3qNK
2cbbyFcV7ozyoVCRKu7vzm/OFCpxDCnzAZtvMz4ZDQKBgQDbxfBt09L+slWuFqyg
Lz4QbM7QV+jtN8lRb4Z/Dqrhg/VizAnbZWK4w4dgmxxCnHwJldrMxF/rxoqgC+7u
OLgH1XQojEdDhqdCJyDIriUScDgLM6Pvg8KovkJreGK3uIb15HoiKhSEFluGNzE/
yG1ORIHxieelWpWjv4stR4V69wKBgQC5GMCJuuQownZQcZC/Ju8+azmzRpksH7vi
nS9qtjQkpq1CGnzWKajDPOWHwDYNPu1dYh1zlStPpukW/K37mlNR2VCvX3Ui0HmI
VnQx/EC0eQc4U+S73HnJJZfXleEJn/3uCHrnd3682wNW0IUaXoDZD8cqXLxMihN2
Ce+rHC4zFQKBgQCBlhYtfDA2VAsnSmFhVlnfL3lG6f8DGNjQ9tS0SH7D2J4xMiRB
xGdQkKJbeTpRoJmWwVCxWr1AdeI5eq/YsYL5w4fpfMVscJJg9FdlXSGo3Jh/KmTo
jqWSABWD7wkuUVTq2lyVloBgXhp7akHU3SZudwCz3l/DZUVEt2WmPjzbgwKBgEa5
8IgwQ5JXjAH9AwQnim9dZXTdWxYDIjXbPg0WhiIjFj0WBfGHhZbkpAgVKBIzo1t1
bC+IJj6PVq2T658iPwgdc7kvToD5DBdOgaO/8bGENYAOfm5SNq7nkHeuK4kT+2GD
GANuI51iSopXryR+S9mlL8M+IC1W7UzDSzMk13ppAoGAdvYkCUncukunNhlth6XU
Hpf3WNOkuQv8t4aBAcwOKQLLWxWENBxb2ukM1lNdU+cMSRb8ETxWvYfFpFcGZzEq
NAy5/Ae79OkKawW4Q8JPY1Vqqan+jjbFR2KHF0wMIKzKSBt/yHqseLul89bXIS7f
OlJJerNhWmWMvbqu5T44Yfw=
-----END PRIVATE KEY-----
''',
}

credentials = ServiceAccountCredentials.from_json_keyfile_dict(
    credentials_dict
)

client = storage.Client(credentials=credentials, project='sudoo-404614')
bucket = client.get_bucket('sudoo-buckets')
blob = bucket.blob(f'models/{model_name}')
blob.upload_from_filename(model_name)
print(f"Upload model {model_name} success!!!")
if os.path.exists(model_name):
  os.remove(model_name)
  print("Remove local model")

# notify to flask server
import requests
try:
  recommendServiceUrl = infomation['recommendServiceUrl']
  body = {
      'version': infomation['versions'][-1],
      'model_name': model_name,
      'download_model_url': f"{infomation['storageUrl']}/{model_name}"
  }
  requests.post(f'{recommendServiceUrl}/update-model', json = body)
  print('Notify recommend server successful!!!')
except:
  print("Something went wrong")

# predict for all user
def mapProduct(recommend_products, product_map):
  def f(index):
    return product_map[index]
  return list(map(f, recommend_products))

def predict(model, user_id, user_info, products):
  size = len(products)
  predict_ratings = model.predict(
      [
        [
            np.array([user_id for i in range(size)]),
            np.array(list(products['_id']))
        ],
        np.array([np.array(list(user_info)) for i in range(size)]),
        np.array([np.array(val) for val in products['product_info']])
      ]
  )
  recommend_products = np.array([array[0] for array in predict_ratings])[::-1].argsort()
  product_id_map = {}
  for i in range(size):
    product_id_map[products.iloc[i]['_id']] = products.iloc[i]['product_id']
  return mapProduct(recommend_products, product_id_map)

for i in range(len(users)):
  recommend_products = predict(model, int(users.iloc[i]['_id']), users.iloc[i]['user_info'], products)
  sudoo.prediction.update_one({'_id': users.iloc[i]['user_id']}, {'$set': {'user_id': users.iloc[i]['user_id'], 'recommend': recommend_products}}, upsert=True)

import time
import os
time.sleep(60)
os.system('sudo poweroff')