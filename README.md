#项目说明



## 可生成的内容

- entity层的注释、属性、getSet、toString
- mapper层的getById、getAll、insert、deleteById、update
- service层的getById、getAll、insert、deleteById、update
- controller层的getById(GET)、getAll(GET)、insert(POST)、deleteById(DELETE)、update(PUT)

在 main/config/myBeetl.properties中设置路径参数,一定要先配置
myBeetl.properties
在 java/beetl/BeetlUtils中运行
可根据json逆向生成实体类和对应Controller
## 运行示例
请输入要创建的文件类名：(格式为：*.java)
QQQ
请输入json对象 最后一行#结束：
{
  "code": 81823419,
  "msg": "ipsum laborum dolore eu elit",
  "data": {
    "order": [
      {
        "orderno": "culpa",
        "name": "eu dolore non",
        "id_number": "610000200205116331",
        "phone": [
          "13386744002",
          "15812718024"
        ],
        "address": [
          {
            "type": "家庭",
            "address": "exercitation in"
          },
          {
            "type": "家庭",
            "address": "qui nisi ut"
          },
          {
            "type": "公司",
            "address": "o"
          }
        ],
        "car_number": "et",
        "car_frame_number": "fugiat sit",
        "car_model": "nisi Excepteur amet",
        "gps_imei": "reprehenderit tempor",
        "src": "自建"
      },
      {
        "orderno": "sunt Duis Lorem do",
        "name": "ut ipsum dolore",
        "id_number": "710000199003075875",
        "phone": [
          "14960799394",
          "14600421154",
          "15674890772",
          "14193309472",
          "13058274488"
        ],
        "address": [
          {
            "type": "家庭",
            "address": "Ut nostrud sunt proident"
          },
          {
            "type": "家庭",
            "address": "occaecat sit enim"
          }
        ],
        "car_number": "veniam reprehenderit",
        "car_frame_number": "dolor ut Lore",
        "car_model": "elit sunt",
        "gps_imei": "eu ullamco proident",
        "src": "自建"
      },
      {
        "orderno": "eu",
        "name": "in nisi Excepteur dolor",
        "id_number": "370000200606083040",
        "phone": [
          "14136791780",
          "17734943861",
          "14989619593",
          "17437806959"
        ],
        "address": [
          {
            "type": "家庭",
            "address": "adipisicing consequat"
          },
          {
            "type": "公司",
            "address": "dolore ullamc"
          },
          {
            "type": "公司",
            "address": "est anim"
          },
          {
            "type": "公司",
            "address": "incididunt aute"
          }
        ],
        "car_number": "eu adipisicing reprehe",
        "car_frame_number": "eiusmod elit",
        "car_model": "esse Duis",
        "gps_imei": "quis",
        "src": "外部"
      }
    ]
  }
}
#


