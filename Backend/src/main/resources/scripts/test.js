var url = "mongodb://localhost:27017/recommender";
var db = connect(url);
db.testdb.update({"title": "good"}, {
    $push: {
        "comments": {
            "com_id": 1,
            "com_content": "你好，请问哪里是精神病院？"
        }
    }
});