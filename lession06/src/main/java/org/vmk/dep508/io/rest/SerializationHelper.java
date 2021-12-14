package org.vmk.dep508.io.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;

import java.io.*;

public class SerializationHelper<T extends Serializable> {

    Class<T> serialazationType;

    public SerializationHelper(Class<T> serialazationType) {
        this.serialazationType = serialazationType;
    }

    private Logger log = Logger.getLogger(getClass());

    ObjectMapper mapper = new ObjectMapper();


    /*
      Необходимо десереализовать объект из файла по указанному пути
     */
    public T loadFromFile(String path) {
        try (BufferedReader ist = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(path)),
                1024*1024))
        {
            String str = "";
            String bigStr = "";
            while ((str = ist.readLine()) != null)
            {
                bigStr += str;
            }
            return parseJson(bigStr);
        }
        catch (IOException exc)
        {
            System.out.println(exc.getMessage());
        }
        return null;
    }

    /*
      Необходимо сохранить сереализованный объект в файл по указанному пути
     */
    public boolean saveToFile(String path, T toSave) {
        try (OutputStream ost = new FileOutputStream(path))
        {
            writeJsonToStream(ost, toSave);
        }
        catch (IOException ex)
        {
            System.out.println(ex.getMessage());
            return false;
        }
        return true;
    }

    public String convertToJsonString(T toConvert) {
        try {
            String json = mapper.writeValueAsString(toConvert);
            return json;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void writeJsonToStream(OutputStream output, T toWrite) {
        try {
            mapper.writeValue(output, toWrite);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public T parseJson(String json) {
        try {
            return mapper.readValue(json, serialazationType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
