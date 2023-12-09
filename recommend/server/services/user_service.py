import datetime
from bson.json_util import dumps
from bson.objectid import ObjectId

class UserService:
    def __init__(self, counter, users):
        self.counter = counter
        self.users = users
    

    def upsertUser(self, body):
        user = {
            'user_id': body['userId'],
            'dob': datetime.datetime.strptime(body['dob'], '%d/%m/%Y'),
            'gender': body['gender']
        }
        _id = self.users.find_one({'user_id': user['user_id']}, {'_id': 1})
        if _id == None:
            userCounter = self.counter.find_one({'collection': 'user'}, {'counter': 1})
            if userCounter == None:
                userCounter = 0
                self.counter.insert_one({'collection': 'user', 'counter': 0})
            else:
                userCounter = int(userCounter['counter']) + 1
                self.counter.update_one({'collection': 'user'}, {'$set': {'counter': userCounter}})
            self.users.update_one({'_id' : int(userCounter)}, { '$set': user }, upsert=True)
        else:
            self.users.update_one({'_id' : int(_id)}, { '$set': user }, upsert=True)
        return user
    
    def getUsers(self):
        return list(self.users.find({}))