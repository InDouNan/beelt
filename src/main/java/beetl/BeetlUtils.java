package beetl;

import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.FileResourceLoader;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.util.*;

/**
 * 豆楠
 *
 * 2018/9/10
 */
public class BeetlUtils {
    static String ClassName="";
    /**
    运行main类生产
     */
    public static void main(String[] args){
            star();
//          MyTemplate myTemplate=config();

//            /*
//            设置Entity属性，生成Entity
//             */
//            myTemplate.setTemplateName("Entity.txt");
//            createEntity(myTemplate);

//            /*
//            生成DAO
//             */
//            myTemplate.setTemplateName("Mapper.txt");
//            createDaoInterface(myTemplate);


//            myTemplate.setTemplateName("Service.txt");
//            createServiceInterface(myTemplate);

//            /*
//            设置Controller属性，生成Controller
//             */
//            myTemplate.setPackageMapPath();
//            myTemplate.setTemplateName("Controller.txt");
//            createController(myTemplate);
    }

    /**
     * 将JsonArray对象转换成String[]
     *
     * @param
     * *JsonArray
     *            json对象
     * @return String[]对象
     * @throws JSONException
     */
    public static String []valueToIndex(JSONArray value){
        String []S=new String[value.length()];
        for (int i=0;i<value.length();i++){
            S[1]=value.get(i).toString();
        }
        return S;
    }

    /**
     * 将Json对象转换成Map
     *
     * @param
     * *jsonObject
     *            json对象
     * @return Map对象
     * @throws JSONException
     */
    @SuppressWarnings("unchecked")
    public static Map<Object, Object> jsonToMap(String jsonString) throws JSONException {

        JSONObject jsonObject = new JSONObject(jsonString);

        Map<Object, Object> result = new HashMap<Object, Object>();
        @SuppressWarnings("unchecked")
        Iterator<Object> iterator = (Iterator<Object>)(Iterator<?>)jsonObject.keys();
        String key = null;
        Object value = null;
        String s;
        while (iterator.hasNext()) {

            key = (String) iterator.next();
            value = jsonObject.get(key);
            if (value instanceof String) {
                result.put(key, (String)value);
            } else if (value instanceof Integer) {
                result.put(key, (Integer)value);
            } else if (value instanceof Double) {
                result.put(key, (Double)value);
            } else if (value instanceof Boolean) {
                result.put(key, (Boolean)value);
            } else if(value instanceof JSONArray){

                if (((JSONArray) value).get(0) instanceof JSONObject) {
                        result.put(key, (JSONArray) value);
                        /**
                         分解json,并传入create，生产新的实体文件
                         */

                        s = ((JSONArray) value).get(0).toString();
                        System.out.println("数组" + s);
                        Map<Object, Object> map = jsonToMap(s);

                        create(key, map);
                        System.out.println("创建java实体类成功！");
                        //循环
                } else {

                        result.put(key, (String[]) valueToIndex((JSONArray)value));
                }

            } else if (value instanceof Object) {
                result.put(key, (Object) value);

                /**
                 分解json,并传入create，生产新的实体文件
                 */
                //循环

                s= JSONObject.valueToString(jsonObject.get(key));
                System.out.println(s);
                Map<Object, Object> map = jsonToMap(s);

                try {
                    create(key, map);
                    System.out.println("创建java实体类成功！");
                }
                catch (Exception e){}
                //循环

            } else {
                result.put(key, value);
            }
        }
        return result;

    }

    /**
    输入json
     */
    public static void star(){

        System.out.println("请输入要创建的文件类名：(格式为：*.java)");
        Scanner s1 = new Scanner(System.in);
        String className = s1.nextLine();
        String jsonString="";
        String check="";
        System.out.println("请输入json对象 最后一行单独以 # 结束：");
        do {
            check=s1.nextLine();
            jsonString += check;
        }
        while(check.charAt(0)!='#');

        Map<Object, Object> map = jsonToMap(jsonString);
       BeetlUtils.ClassName=new String(className);
        create(className,map);

        System.out.println("创建java实体类成功！");
        s1.close();
    }
    /**
    模板定义与配置
     */
    public  MyTemplate config( List<MongoFields>mongFieldList,String ClassName){
        MyTemplate myTemplate = new MyTemplate();

        try{
        //          加载配置文件
        String dir = System.getProperty("user.dir")
                + File.separator + "src" + File.separator + "main" + File.separator + "config" + File.separator;
        String fileName = "myBeetl.properties";
        Properties properties = new Properties();
        properties.load(new FileInputStream(dir + fileName));
        //构建模板对象

    /**
     设置公共属性：实体类名，模块包名
    */

        myTemplate.setRootPath(properties.getProperty("RootPath"));
//            myTemplate.setRootPackageName(RootPackageName);
        myTemplate.setEntityClassName(ClassName);
        myTemplate.setEntityName(properties.getProperty(ClassName.toLowerCase()));
        myTemplate.setPackageName(properties.getProperty("PackageName"));
//        myTemplate.setCollectionName(properties.getProperty("CollectionName"));
        myTemplate.setList(mongFieldList);

        }catch(Exception e){
        e.printStackTrace();
        }
    return myTemplate;
    }
    /**
     生成模板文件
     */
    public static void create(String className,Map<Object,Object>map){

        try {
            List<MongoFields>mongoFieldsList=generateFile(className, map);
                        /*
            设置Controller属性，生成Controller
             */

            MyTemplate myTemplate=new BeetlUtils().config(mongoFieldsList,className);
            myTemplate.setPackageMapPath();
            System.out.println(className+ClassName);

            if ( BeetlUtils.ClassName.equals(className)){
            myTemplate.setTemplateName("Controller.txt");
            createController(myTemplate);
            /*
             生成Service
             */
            myTemplate.setTemplateName("Service.txt");
            createServiceInterface(myTemplate);

            }
            myTemplate.setTemplateName("Entity.txt");
            createEntity(myTemplate);
        }
        catch (Exception e){
            e.printStackTrace();
        }


    }
    public static void createController(MyTemplate myTemplate){
        String filePath = myTemplate.getPackagePath() + "controller";
        String fileName = myTemplate.getEntityClassName() + "Controller.java";
        createJavaFile(myTemplate,filePath,fileName);
    }
    public static void createEntity(MyTemplate myTemplate){
        String filePath = myTemplate.getPackagePath() + "entity";
        String fileName = myTemplate.getEntityClassName() + ".java";
        createJavaFile(myTemplate,filePath,fileName);
    }
    public static void createServiceInterface(MyTemplate myTemplate){
        String filePath = myTemplate.getPackagePath() + "service";

        String fileName = myTemplate.getEntityClassName() + "Service.java";
        createJavaFile(myTemplate,filePath,fileName);
    }
    public static void createDaoInterface(MyTemplate myTemplate){
        String filePath = myTemplate.getPackagePath() + "mapper";
        String fileName = myTemplate.getEntityClassName() + "Mapper.java";
        createJavaFile(myTemplate,filePath,fileName);
    }
    /**
     * 将Map对象转换成List<MongoFields>
     *
     * @param
     * *Object
     *            MongoFields对象
     * @return List<MongoFields>对象
     */
    public static  List<MongoFields> generateFile(String className, Map<Object, Object> map) throws IOException {
        List<MongoFields> mongoFieldsList = new ArrayList<MongoFields>();

            for(Map.Entry<Object, Object> entry : map.entrySet()) {
                Object value = entry.getValue();
                MongoFields mongoFields ;
                if (value instanceof String) {
                    mongoFields = new MongoFields();
                    mongoFields.setType("String");
                    mongoFields.setName(entry.getKey().toString());
                    mongoFieldsList.add(mongoFields);
                } else if (value instanceof Integer) {
                    mongoFields = new MongoFields();
                    mongoFields.setType("int");
                    mongoFields.setName(entry.getKey().toString());
                    mongoFieldsList.add(mongoFields);
                } else if (value instanceof Double) {
                    mongoFields = new MongoFields();
                    mongoFields.setType("double");
                    mongoFields.setName(entry.getKey().toString());
                    mongoFieldsList.add(mongoFields);
                } else if (value instanceof Boolean) {
                    mongoFields = new MongoFields();
                    mongoFields.setType("boolean");
                    mongoFields.setName(entry.getKey().toString());
                    mongoFieldsList.add(mongoFields);
                }else if (value instanceof JSONArray) {
                    mongoFields = new MongoFields();
                    mongoFields.setType("List<"+entry.getKey()+">");
                    mongoFields.setName(entry.getKey().toString()+"Index");
                    mongoFieldsList.add(mongoFields);

                }else if( value instanceof String[]){
                    mongoFields = new MongoFields();
                    mongoFields.setType("String []");
                    mongoFields.setName(entry.getKey().toString());
                    mongoFieldsList.add(mongoFields);

                } else if (value instanceof Object) {
                    mongoFields = new MongoFields();
                    mongoFields.setType("Object");
                    mongoFields.setName(entry.getKey().toString());
                    mongoFieldsList.add(mongoFields);

                }

//                else if ( value instanceof int[]){
//                    mongoFields = new MongoFields();
//                    mongoFields.setType("int []");
//                    mongoFields.setName(entry.getKey().toString());
//                    mongoFieldsList.add(mongoFields);
//                }
//                else if ( value instanceof double[]){
//                    mongoFields = new MongoFields();
//                    mongoFields.setType("double []");
//                    mongoFields.setName(entry.getKey().toString());
//                    mongoFieldsList.add(mongoFields);
//                } else if ( value instanceof Boolean[]){
//                    mongoFields = new MongoFields();
//                    mongoFields.setType("Boolean []");
//                    mongoFields.setName(entry.getKey().toString());
//                    mongoFieldsList.add(mongoFields);
//                }
            }
        return mongoFieldsList;

    }
    /**
     生成模板文件
     */
    public static void createJavaFile(MyTemplate myTemplate,String filePath,String fileName){
        try{
            //指定模板路径
            String root = System.getProperty("user.dir") + File.separator + "newTemplate" + File.separator;
            FileResourceLoader resourceLoader = new FileResourceLoader(root,"utf-8");
            Configuration cfg = Configuration.defaultConfiguration();
            GroupTemplate gt = new GroupTemplate(resourceLoader, cfg);
            //指定要加载的模板
            Template t = gt.getTemplate(myTemplate.getTemplateName());
            //绑定全局变量
            t.binding("myUtil",myTemplate);
            //读取模板输出的文本
            String str = t.render();
            System.out.println(str);
            File dir = new File(myTemplate.getRootPath() + File.separator + filePath);
            if(!dir.exists() && !dir.isDirectory()){
                dir.mkdirs();
            }
            File file = new File( dir + File.separator + fileName );
            if(!file.exists()){
                file.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fileWriter);
            bw.write(str);
            bw.flush();
            bw.close();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

    }

//    public static List<MongoFields> getMongFieldList(String fileName) throws Exception{
//        List<MongoFields> mongoFieldsList = new ArrayList<MongoFields>();
//        ObjectMapper objectMapper = new ObjectMapper();
//        List<Map<String,Object>> list =
//                (List<Map<String,Object>>)objectMapper.readValue(new FileInputStream(System.getProperty("user.dir") + File.separator + "sql" + File.separator + fileName), List.class);
//        for(Map<String,Object> map : list){
//            MongoFields mongoFields = new MongoFields();
//            mongoFields.setName(map.get("name").toString());
//            mongoFields.setType(map.get("type").toString());
//            mongoFields.setNull((Boolean) map.get("isNull"));
//            mongoFields.setIndex((Boolean)map.get("isIndex"));
//            mongoFields.setDefaultValue(map.get("defaultValue"));
//            if(map.containsKey("autoIncrement")){
//                mongoFields.setAutoIncrement((Boolean)map.get("autoIncrement"));
//            }
//            mongoFields.setCamelCaseName(map.get("name").toString().substring(0, 1).toUpperCase() + map.get("name").toString().substring(1));
//            if(map.get("description") != null)
//            mongoFields.setDescription(map.get("description").toString());
//            mongoFieldsList.add(mongoFields);
//        }
//        return mongoFieldsList;
//    }
}
