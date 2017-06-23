package com.dower.demo.comm.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

import org.nustaq.serialization.FSTObjectInput;
import org.nustaq.serialization.FSTObjectOutput;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.MessageLite;

/**
 * Created by zhujiajia on 15/11/10.
 */
public class SerializationUtils {
    public SerializationUtils() {  }

    // Serialize
    //-----------------------------------------------------------------------

    //    In order to optimize object reuse and thread safety,
    // FSTConfiguration provides 2 simple factory methods to
    // obtain input/outputstream instances (they are stored thread local):
    //! reuse this Object, it caches metadata. Performance degrades massively
    //using createDefaultConfiguration()        FSTConfiguration is singleton

    /**
     * <p>Serializes an <code>Object</code> to a byte array for
     * storage/serialization.</p>
     *
     * @param obj the object to serialize to bytes
     * @return a byte[] with the converted Serializable
     * @throws SerializationUtils (runtime) if the serialization fails
     */
    private static byte[] fastSerialize (Object obj) {
        ByteArrayOutputStream byteArrayOutputStream = null;
        FSTObjectOutput out = null;
        try {
            // stream closed in the finally
            byteArrayOutputStream = new ByteArrayOutputStream(512);
            out = new FSTObjectOutput(byteArrayOutputStream);  //32000  buffer size
            out.writeObject(obj);
            out.flush();
            return byteArrayOutputStream.toByteArray();
        } catch (IOException ex) {

        } finally {
            try {
                obj = null;
                if (out != null) {
                    out.close();    //call flush byte buffer
                    out = null;
                }
                if (byteArrayOutputStream != null) {

                    byteArrayOutputStream.close();
                    byteArrayOutputStream = null;
                }
            } catch (IOException ex) {
                // ignore close exception
            }
        }
        return byteArrayOutputStream.toByteArray();
    }
    // Deserialize
    //-----------------------------------------------------------------------

    /**
     * <p>Deserializes a single <code>Object</code> from an array of bytes.</p>
     *
     * @param objectData the serialized object, must not be null
     * @return the deserialized object
     * @throws IllegalArgumentException if <code>objectData</code> is <code>null</code>
     * @throws Exception     (runtime) if the serialization fails
     */
    private static Object fastDeserialize(byte[] objectData) throws Exception {
        ByteArrayInputStream byteArrayInputStream = null;
        FSTObjectInput in = null;
        try {
            // stream closed in the finally
            byteArrayInputStream = new ByteArrayInputStream(objectData);
            in = new FSTObjectInput(byteArrayInputStream);
            return in.readObject();
        } catch (ClassNotFoundException ex) {
            throw new Exception(ex);
        } catch (IOException ex) {
            throw new Exception(ex);
        } finally {
            try {
                objectData = null;
                if (in != null) {
                    in.close();
                    in = null;
                }
                if (byteArrayInputStream != null) {
                    byteArrayInputStream.close();
                    byteArrayInputStream = null;
                }
            } catch (IOException ex) {
                // ignore close exception
            }
        }
    }


    /**
     * 将对象序列化为字节数组
     *
     * @param obj
     * @return 字节数组
     * @throws Exception
     */
    public static byte[] kryoSerialize(Object obj) throws Exception {
        Kryo _kryo = new Kryo();
        _kryo.setReferences(false);
        Output output = null;
        try {
            ByteArrayOutputStream byteArrayOutputStream =new ByteArrayOutputStream();
//            byte [] buffer =new byte[4096];
            output = new Output(byteArrayOutputStream);
            _kryo.writeClassAndObject(output, obj);
            output.flush();
            return byteArrayOutputStream.toByteArray();
        }finally {
            if(output!=null){
                output.close();
            }
        }
    }


    /**
     * 将字节数组反序列化为对象
     *
     * @param bytes 字节数组
     * @return object
     * @throws Exception
     */
    public static Object kryoDeserialize(byte[] bytes) throws Exception {
        Kryo _kryo = new Kryo();
        _kryo.setReferences(false);
        ByteArrayInputStream byteArrayInputStream =new ByteArrayInputStream(bytes);
        if(bytes == null || bytes.length==0){
            return  null;
        }
        Input input =null;
        try {
            input = new Input(byteArrayInputStream);
            return _kryo.readClassAndObject(input);
        }finally {
            if(input != null)
                input.close();
        }
    }



    //jdk原生序列方案

    /**
     * @param obj
     * @return
     */
    private static byte[] jserialize(Object obj) throws Exception{
        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(obj);
            return baos.toByteArray();
        } catch (Exception e) {
            throw new Exception(e);
        } finally {
            if (oos != null)
                try {
                    oos.close();
                    baos.close();
                } catch (IOException e) {
                }
        }
    }

    /**
     * @param bits
     * @return
     */
    private static Object jdeserialize(byte[] bits) throws Exception{
        ObjectInputStream ois = null;
        ByteArrayInputStream bais = null;
        try {
            bais = new ByteArrayInputStream(bits);
            ois = new ObjectInputStream(bais);
            return ois.readObject();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        } finally {
            if (ois != null)
                try {
                    ois.close();
                    bais.close();
                } catch (IOException e) {
                }
        }
    }


    // 基于protobuffer的序列化方案

    /**
     * @param bytes       字节数据
     * @param messageLite 序列化对应的类型
     * @return
     * @throws Exception
     */
    private static MessageLite protoDeserialize(byte[] bytes, MessageLite messageLite) throws Exception {
        assert (bytes != null && messageLite != null);
        try {
            return messageLite.getParserForType().parsePartialFrom(CodedInputStream.newInstance(bytes), ExtensionRegistryLite.getEmptyRegistry());
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param messageLite 序列化对应的类型
     * @return
     * @throws Exception
     */
    private static byte[] protoSerialize(MessageLite messageLite) throws Exception {
        assert (messageLite != null);
        return messageLite.toByteArray();
    }


    public static void main(String[]args) {
        String gen = "http://www.autohome.com.cn/grade/carhtml/";
        Map<String,String> map = new HashMap<String, String>();
        Map<String,String> map2 = new HashMap<String, String>();
        byte [] get=null;
        //char[] charArray = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
        for(int i=0; i<10000 ;i++){
            map.put(gen+i,gen+gen+i);
        }
//        HashMap map=new HashMap();
//        map.put("name","朱佳佳");
//        HashMap getmap=new HashMap();
//        StringBuffer buffer=new StringBuffer();
//        byte[]test={1,0,106,97,118,97,46,117,116,105,108,46,72,97,115,104,77,97,-16,1,3,110,97,109,-27,3,-124,-26,-100,-79,-28,-67,-77,-28,-67,-77};
        try {
//            byte [] mapbytes=SerializationUtils.kryoSerialize(map);
//            for(int i=0;i<mapbytes.length;i++){
//                buffer.append(mapbytes[i]+",");
//            }
            get = SerializationUtils.kryoSerialize(map);
            map2 = (HashMap)kryoDeserialize(get);
            System.out.print(get.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(get.length);
        System.out.println(map2.get("http://www.autohome.com.cn/grade/carhtml/0"));

    }


}
