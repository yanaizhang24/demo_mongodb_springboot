package cn.yanf.StringUtils;

import cn.yanf.Repository.CustomerRepository;
import cn.yanf.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.stereotype.Component;


import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/8/30 0030.
 */
@Component
public class SpringUtils implements Serializable,CommandLineRunner {
    @Autowired
    private CustomerRepository repository;
    private static String message;
    public  static void getMessage (String message) throws Exception {
        SpringUtils.message=message;
    }

    @Override
    public void run(String... strings) throws Exception {
        if(strings.length>0)
            repository.save(new Customer("102160613134951192168402401009",strings[0]));
    }
    public  static <T> boolean writeData(List<T> list,String fileName){
        File file=new File(fileName);
        if(file.exists()){//文件已存在
            return false;
        }else{
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            FileWriter fileWriter=new FileWriter(file.getName(),true);
            BufferedWriter bufferedWriter=new BufferedWriter(fileWriter);
            Date date=new Date();
            DateFormat dateFormatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            bufferedWriter.write(dateFormatter.format(date));
            bufferedWriter.newLine();
            for(T t:list){
                bufferedWriter.write(t.toString());
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
