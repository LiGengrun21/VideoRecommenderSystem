var url = "mongodb://localhost:27017/recommender";
var db = connect(url);
//开始循环
for(let i=1;i<=671;i++)
{
    db.User.insert({userId:i,
    username:"Tom"+i,
    password:"123",
    email:"Tom"+i+"@outlook.com",
    avatar:"http://localhost:8099/userAvatars/default.jpg",
    deleted:0});
}