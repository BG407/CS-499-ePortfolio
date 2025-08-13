from pymongo import MongoClient
from pymongo.errors import PyMongoError

class AnimalShelter:
    def __init__(self, username, password):
        try:
            self.client = MongoClient(f"mongodb://{username}:{password}@localhost:27017")
            self.database = self.client['AAC']
        except PyMongoError as e:
            print(f"Connection Error: {e}")

    def create(self, data):
        if data is not None:
            try:
                result = self.database.animals.insert_one(data)
                return result.acknowledged
            except PyMongoError as e:
                print(f"Insert Error: {e}")
                return False
        return False

    def read(self, query):
        try:
            result = self.database.animals.find(query)
            return list(result)
        except PyMongoError as e:
            print(f"Read Error: {e}")
            return []

    def update(self, query, new_values):
        try:
            result = self.database.animals.update_many(query, {'$set': new_values})
            return result.modified_count
        except PyMongoError as e:
            print(f"Update Error: {e}")
            return 0

    def delete(self, query):
        try:
            result = self.database.animals.delete_many(query)
            return result.deleted_count
        except PyMongoError as e:
            print(f"Delete Error: {e}")
            return 0
