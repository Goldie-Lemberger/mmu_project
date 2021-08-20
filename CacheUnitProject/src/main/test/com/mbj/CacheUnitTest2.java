package main.test.com.mbj;


import java1.algorithm.LRUAlgoCacheImpl;
import main.java.com.mbj.dao.DaoFileImpl;
import main.java.com.mbj.dm.DataModel;
import main.java.com.mbj.memory.CacheUnit;
import org.junit.*;




public class CacheUnitTest2 {

    /**
     * test the CacheUnit
     */
    @Test
    public void testCachUnit(){
        LRUAlgoCacheImpl<Long, DataModel<String>> algo=new LRUAlgoCacheImpl<>(5);
        CacheUnit<String> cache=new CacheUnit<>(algo);
        DataModel<String>[] dm = new DataModel[6];
        dm[0]=new DataModel<String>(0L,"a");
        dm[1]=new DataModel<String>(1L,"b");
        dm[2]=new DataModel<String>(2L,"c");
        dm[3]=new DataModel<String>(3L,"d");
        dm[4]=new DataModel<String>(4L,"e");
        dm[5]=new DataModel<String>(5L,"f");
        DataModel<String>[] expectedOutput = new DataModel[6];
        expectedOutput[0]=null;
        expectedOutput[1]=null;
        expectedOutput[2]=null;
        expectedOutput[3]=null;
        expectedOutput[4]=null;
        expectedOutput[5]=dm[0];
        DataModel<String>[] actualOutput=cache.putDataModels(dm);
        Assert.assertEquals(actualOutput[0],expectedOutput[0]);
        Assert.assertEquals(actualOutput[1],expectedOutput[1]);
        Assert.assertEquals(actualOutput[2],expectedOutput[2]);
        Assert.assertEquals(actualOutput[3],expectedOutput[3]);
        Assert.assertEquals(actualOutput[4],expectedOutput[4]);
        Assert.assertEquals(actualOutput[5],expectedOutput[5]);
    }

    /**
     * test the DataModel
     */
    @Test
    public void testDataModel(){
        DataModel<String> dm=new DataModel<String>(0L,"a");
        DataModel<String> dm2=new DataModel<String>(1L,"b");
        Assert.assertEquals(dm.getDataModelId().intValue(),0);
        Assert.assertEquals(dm.getContent(),"a");
        Assert.assertNotEquals(dm, dm2);
        DataModel<String> dm3=new DataModel<String>(1L,"b");
        Assert.assertEquals(dm2,dm3);
    }

    /**
     * test the DaoFileImpl
     */

    @Test
    public void testDaoFileImpl(){
        DaoFileImpl<String> dao=new DaoFileImpl<>("src\\main\\resources\\datasourse.json",20);
        DataModel<String> dm=new DataModel<String>(2L,"aa");
        DataModel<String> dm3=new DataModel<String>(0L,"a");
        DataModel<String> dm2=new DataModel<String>(3L,"bb");
        dao.save(dm);
        dao.save(dm2);
        Assert.assertEquals(dao.find(0L),dm3);
        dao.delete(dm3);
    }

    /**
     * test the all the system
     */
    @Test
    public void CacheUnitTest(){
        LRUAlgoCacheImpl<Long, DataModel<String>> algo=new LRUAlgoCacheImpl<>(5);
        CacheUnit<String> cache=new CacheUnit<>(algo);
        DaoFileImpl<String> dao=new DaoFileImpl<>("src\\main\\resources\\datasourse.json",20);
        Long ids[]=new Long[2];
        ids[0]=0L;
        ids[1]=1L;


        DataModel<String>[] dm = new DataModel[6];
        dm[0]=new DataModel<String>(0L,"a");
        dm[1]=new DataModel<String>(1L,"b");
        dm[2]=new DataModel<String>(2L,"c");
        dm[3]=new DataModel<String>(3L,"d");
        dm[4]=new DataModel<String>(4L,"e");
        dm[5]=new DataModel<String>(5L,"f");

        if(cache.getDataModels(ids)[0]==null && cache.getDataModels(ids)[1]==null){
            cache.putDataModels(dm);
            for (DataModel<String> stringDataModel : dm) {
                dao.save(stringDataModel);
            }
        }


        Assert.assertEquals(cache.getDataModels(ids)[1],dm[1]);
        Assert.assertNull(cache.getDataModels(ids)[0]);

        ids[0]=0L;
        ids[1]=5L;

        DataModel<String>[] dm2 = new DataModel[1];
        dm2[0]=dao.find(0L);
        dao.save(cache.putDataModels(dm2)[0]);
    }
}
