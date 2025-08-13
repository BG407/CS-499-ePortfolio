from pymongo import MongoClient, ReturnDocument
from pymongo.errors import PyMongoError, ConfigurationError
import re


class AnimalShelter:
    def __init__(self, username, password, host='localhost', port=27017):
        try:
            # Establish secure connection string format
            uri = f"mongodb://{username}:{password}@{host}:{port}"
            self.client = MongoClient(uri, serverSelectionTimeoutMS=5000)
            self.database = self.client['AAC']
            # Trigger connection check
            self.client.admin.command('ping')
        except ConfigurationError as e:
            print(f"Configuration Error: {e}")
        except PyMongoError as e:
            print(f"Connection Error: {e}")

    def is_valid_query(self, query):
        """
        Basic input validator to ensure query is a dictionary.
        Can be expanded further for production use.
        """
        return isinstance(query, dict)

    def create(self, data):
        if data and isinstance(data, dict):
            try:
                result = self.database.animals.insert_one(data)
                return result.acknowledged
            except PyMongoError as e:
                print(f"Insert Error: {e}")
        else:
            print("Invalid input: 'data' must be a non-empty dictionary.")
        return False

    def read(self, query):
        if self.is_valid_query(query):
            try:
                result = self.database.animals.find(query)
                return list(result)
            except PyMongoError as e:
                print(f"Read Error: {e}")
        else:
            print("Invalid query input for read operation.")
        return []

    def update(self, query, new_values):
        if self.is_valid_query(query) and isinstance(new_values, dict):
            try:
                result = self.database.animals.update_many(query, {'$set': new_values})
                return result.modified_count
            except PyMongoError as e:
                print(f"Update Error: {e}")
        else:
            print("Invalid input for update operation.")
        return 0

    def delete(self, query):
        if self.is_valid_query(query):
            try:
                result = self.database.animals.delete_many(query)
                return result.deleted_count
            except PyMongoError as e:
                print(f"Delete Error: {e}")
        else:
            print("Invalid query input for delete operation.")
        return 0
