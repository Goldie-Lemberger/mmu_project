package main.test.com.mbj;

import com.google.gson.Gson;
import java1.algorithm.LRUAlgoCacheImpl;
import main.java.com.mbj.dao.DaoFileImpl;
import main.java.com.mbj.dm.DataModel;
import main.java.com.mbj.memory.CacheUnit;
import main.java.com.mbj.server.Request;
import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CacheUnitTest {

    @Test
    public void testCacheUnit() throws IOException {
        DaoFileImpl<String> dao = new DaoFileImpl<>("src\\main\\resources\\datasourse.json", 3);
        CacheUnit<String> cache = new CacheUnit<>(new LRUAlgoCacheImpl<>(3));
        DataModel<String>[] arr = new DataModel[7];
        arr[0] = new DataModel<>(1L, "ayala");
        arr[1] = new DataModel<>(2L, "chava");
        arr[2] = new DataModel<>(3L, "orian");
        arr[3] = new DataModel<>(4L, "miri");
        arr[4] = new DataModel<>(5L, "pnina");
        arr[5] = new DataModel<>(6L, "racheli");
        arr[6] = new DataModel<>(7L, "tamar");
        DataModel<String>[] removed;
        removed = cache.putDataModels(arr);
        for (DataModel<String> data : arr) {
            if (!Arrays.asList(removed)
                    .contains(data)) {
                dao.save(data);
            }
        }
        DataModel<String> data8 = dao.find(5L);
        Assert.assertEquals("find failed", data8, arr[4]);
        dao.delete(data8);
        Long[] ids = {5L};
        cache.removeDataModels(ids);
        data8 = dao.find(5L);
        Assert.assertNull("delete failed", data8);
    }

    @Test
    public void testC() {

        Map<String, String> headerReq = new HashMap<>();
        headerReq.put("action", "GET");
        DataModel<String>[] dmArray = new DataModel[]{new DataModel<String>(7L, "g"),new DataModel<String>(9L, "ayala")};

        Request<DataModel<String>[]> req = new Request<>(headerReq, dmArray);

        Gson gson =new Gson();
        try {
            Socket socket = new Socket("127.0.0.1", 12345);
            DataOutputStream writer = new DataOutputStream(socket.getOutputStream());
            writer.writeUTF(gson.toJson(req));
            writer.flush();
            DataInputStream input = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String content = "";
            do{
                content = input.readUTF();
                sb.append(content);
            }
            while (input.available() != 0);
            content = sb.toString();
            String response = "true";
            //Type requestType = new TypeToken<DataModel<String>[]>() {
            //}.getType();
            //DataModel<String>[] request = new Gson().fromJson(content, requestType);
            //response = new Gson().fromJson(content);
            System.out.println("message from server: " + content);


        } catch ( IOException e) {
            e.printStackTrace();
        }

    }
}

/*
package main.test.com.company.memory;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import main.java.com.company.dm.DataModel;
import main.java.com.company.server.Request;
import org.junit.Test;

import java.io.*;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class testPartC {

    private BufferedReader br;

    @Test
    public void testC() {

        Map<String, String> headerReq = new HashMap<>();
        headerReq.put("action", "GET");
        DataModel<String>[] dmArray = new DataModel[]{new DataModel<String>(1L, "a"),new DataModel<String>(2L, "b")};

        Request<DataModel<String>[]> req = new Request<>(headerReq, dmArray);

        Gson gson =new Gson();
        try {
            Socket socket = new Socket("127.0.0.1", 12345);
            DataOutputStream writer = new DataOutputStream(socket.getOutputStream());
            writer.writeUTF(gson.toJson(req));
            writer.flush();
            DataInputStream input = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String content = "";
            do{
                content = input.readUTF();
                sb.append(content);
            }
            while (input.available() != 0);
            content = sb.toString();
            //Boolean response = true;
            Type requestType = new TypeToken<Request<DataModel<String>[]>>() {
            }.getType();
            Request<DataModel<String>[]> request = new Gson().fromJson(content, requestType);
            //response = new Gson().fromJson(content,response.getClass());
            System.out.println("message from server: " + request);


        } catch ( IOException e) {
            e.printStackTrace();
        }

    }
}

 */