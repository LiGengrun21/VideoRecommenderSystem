var url = "mongodb://localhost:27017/recommender";
var db = connect(url);
db.Admin.aggregate([{ $match: { adminId: { $type: "double" }} }, { $project: { _id: 0, adminId: { $toInt: "$adminId" }, otherFields: 1 } }, { $out: "NewAdmin" }])
