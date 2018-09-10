package beetl;



import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PojoFactory {

    public static void Test(String[] args) throws Exception {

        System.out.println("请输入要创建的类名：(格式为：*.java)");
        Scanner s1 = new Scanner(System.in);
        String className = s1.nextLine();
        String jsonString="";
        String check="";
        System.out.println("请输入json对象 最后一行#结束：");
        do {
            check=s1.nextLine();
            jsonString += check;
        }
        while(check.charAt(0)!='#');
        //String jsonString = "{id : 10, name : '小明', age : 18, score : 88.5, city : '北京', isgood : true}";
        Map<Object, Object> map = jsonToMap(jsonString);
        generateFile(className, map);
        System.out.println("创建java实体类成功！");
        s1.close();
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
            }else if (value instanceof JSONArray) {
                result.put(key, (JSONArray)value);
                //循环
                /*****************************/
                s=((JSONArray) value).get(0).toString();
                System.out.println("数组"+s);
                Map<Object, Object> map = jsonToMap(s);
                try {
                    generateFile(key+".java", map);
                    System.out.println("创建java实体类成功！");
                }
                catch (Exception e){}
                //循环
                /*****************************/
            }else if (value instanceof Object) {
                result.put(key, (Object) value);
                /*****************************/
                //循环
                s= JSONObject.valueToString(jsonObject.get(key));
                System.out.println(s);
                Map<Object, Object> map = jsonToMap(s);
                try {
                    generateFile(key+".java", map);
                    System.out.println("创建java实体类成功！");
                }
                catch (Exception e){}
                //循环
                /*****************************/


            }
            else {
                result.put(key, value);
            }
        }
        return result;

    }

    public static void generateFile(String className, Map<Object, Object> map) throws IOException {

        String path = PojoFactory.class.getResource("").getPath();
        path = path.replace("bin", "src")+"/"+className;
        System.out.println(path);
        File f = new File(path);
        if (!f.isFile()) {
            f.createNewFile();
        }
        FileWriter fw = null;
        BufferedWriter bw = null;
        try {
            fw = new FileWriter(f);
            bw = new BufferedWriter(fw);
            bw.write("package "+PojoFactory.class.getPackage().getName()+";");
            bw.write("\n\r");
            bw.write("public class "+className.replace(".java", "")+" {\n\r");

            for(Map.Entry<Object, Object> entry : map.entrySet()) {
                Object value = entry.getValue();
                if (value instanceof String) {
                    bw.write("\tprivate String "+entry.getKey()+";\n");
                } else if (value instanceof Integer) {
                    bw.write("\tprivate int "+entry.getKey()+";\n");
                } else if (value instanceof Double) {
                    bw.write("\tprivate double "+entry.getKey()+";\n");
                } else if (value instanceof Boolean) {
                    bw.write("\tprivate boolean "+entry.getKey()+";\n");
                }else if (value instanceof JSONArray) {
                    bw.write("\tprivate List<"+entry.getKey()+"Index"+"> "+entry.getKey()+";\n");
                } else if (value instanceof Object) {
                    bw.write("\tprivate Object "+entry.getKey()+";\n");
                }
            }

            bw.write("\n");

            for(Map.Entry<Object, Object> entry : map.entrySet()) {
                Object value = entry.getValue();
                String name = entry.getKey().toString();
                String getName = "get" + name.substring(0,1).toUpperCase() + name.substring(1);
                String setName = "set" + name.substring(0,1).toUpperCase() + name.substring(1);
                if (value instanceof String) {
                    bw.write("\tpublic void "+ setName+"(String " + name + ") {\n");
                    bw.write("\t\t"+"this." + name+ " = "+ name + ";\n\t}\n\r");

                    bw.write("\tpublic String "+ getName+"() {\n");
                    bw.write("\t\t"+"return " + name + ";\n\t}\n\r");
                } else if (value instanceof Integer) {
                    bw.write("\tpublic void "+ setName+"(int " + name + ") {\n");
                    bw.write("\t\t"+"this." + name+ " = "+ name + ";\n\t}\n\r");

                    bw.write("\tpublic int "+ getName+"() {\n");
                    bw.write("\t\t"+"return " + name + ";\n\t}\n\r");
                } else if (value instanceof Double) {
                    bw.write("\tpublic void "+ setName+"(double " + name + ") {\n");
                    bw.write("\t\t"+"this." + name+ " = "+ name + ";\n\t}\n\r");

                    bw.write("\tpublic double "+ getName+"() {\n");
                    bw.write("\t\t"+"return " + name + ";\n\t}\n\r");
                } else if (value instanceof Boolean) {
                    bw.write("\tpublic void "+ setName+"(boolean " + name + ") {\n");
                    bw.write("\t\t"+"this." + name+ " = "+ name + ";\n\t}\n\r");

                    bw.write("\tpublic boolean "+ getName+"() {\n");
                    bw.write("\t\t"+"return " + name + ";\n\t}\n\r");
                }else if (value instanceof JSONArray) {
                    bw.write("\tpublic void "+ setName+"(List<"+name+"Index"+"> " + name + ") {\n");
                    bw.write("\t\t"+"this." + name+ " = "+ name + ";\n\t}\n\r");

                    bw.write("\tpublic List<"+name+"Index"+"> "+ getName+"() {\n");
                    bw.write("\t\t"+"return " + name + ";\n\t}\n\r");
                }else if (value instanceof Object) {
                    bw.write("\tpublic void "+ setName+"(Object " + name + ") {\n");
                    bw.write("\t\t"+"this." + name+ " = "+ name + ";\n\t}\n\r");

                    bw.write("\tpublic Object "+ getName+"() {\n");
                    bw.write("\t\t"+"return " + name + ";\n\t}\n\r");
                }
            }

            bw.write("}");
        } finally {
            if (bw != null) {
                bw.close();
                bw = null;
            }
            if (fw != null) {
                fw.close();
                fw = null;
            }
        }
    }

}
