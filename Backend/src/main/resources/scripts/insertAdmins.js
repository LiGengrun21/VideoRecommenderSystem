var url = "mongodb://localhost:27017/recommender";
var db = connect(url);
//插入超级管理员
db.Admin.insert({adminId:1,
    adminName:"Frank",
    password: "123",
    email:"Frank@outlook.com",
    avatar:"http://localhost:8099/adminAvatars/default.jpg",
    deleted: 0,
    permission:1})
//插入普通管理员
for(let i=2;i<=20;i++)
{
    db.Admin.insert({adminId:i,
        adminName:"Jerry"+i,
        password: "123",
        email:"Jerry"+i+"@outlook.com",
        avatar:"http://localhost:8099/adminAvatars/default.jpg",
        deleted: 0,
        permission:0})
}