package zc.dbtoplist;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.dd.plist.NSArray;
import com.dd.plist.NSDictionary;
import com.dd.plist.PropertyListParser;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List<AddressEntity> provinces = MyApplication.getDaoInstant().getAddressEntityDao().queryBuilder().where(AddressEntityDao.Properties.ParentId.eq(0)).list();
        NSArray provinceArray = new NSArray(provinces.size());
        for (int i = 0; i < provinces.size(); i++) {
            NSDictionary stateDic = new NSDictionary();
            stateDic.put("state", provinces.get(i).getName());
            stateDic.put("id", provinces.get(i).getId());
            List<AddressEntity> cities = MyApplication.getDaoInstant().getAddressEntityDao().queryBuilder().where(AddressEntityDao.Properties.ParentId.eq(provinces.get(i).getId())).list();
            NSArray cityArray = new NSArray(cities.size());
            for (int j = 0; j < cities.size(); j++) {
                NSDictionary cityDic = new NSDictionary();
                cityDic.put("city", cities.get(j).getName());
                cityDic.put("id", cities.get(j).getId());
                List<AddressEntity> areas = MyApplication.getDaoInstant().getAddressEntityDao().queryBuilder().where(AddressEntityDao.Properties.ParentId.eq(cities.get(j).getId())).list();
                NSArray areaArray = new NSArray(areas.size());
                for (int n = 0; n < areas.size(); n++) {
                    areaArray.setValue(n, areas.get(n).getName());
                }
                cityDic.put("areas", areaArray);
                cityArray.setValue(j, cityDic);
            }
            stateDic.put("cities", cityArray);
            provinceArray.setValue(i, stateDic);
        }

        File sdCardDir = Environment.getExternalStorageDirectory();
        File sdFile = new File(sdCardDir, "area.plist");
        try {
            PropertyListParser.saveAsXML(provinceArray, sdFile);
            Log.i("Test", "saveAsXML");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void savePlist() throws ParseException, IOException {
        NSDictionary root = new NSDictionary();
        NSArray people = new NSArray(2);
        NSDictionary person1 = new NSDictionary();
        person1.put("Name", "Peter");
        person1.put("Age", 23);

        NSDictionary person2 = new NSDictionary();
        person2.put("Name", "Lisa");
        person2.put("Age", 42);

        people.setValue(0, person1);
        people.setValue(1, person2);
        root.put("People", people);
//        PropertyListParser.saveAsXML(root, new File("people.plist"));
        File sdCardDir = Environment.getExternalStorageDirectory();
        File sdFile = new File(sdCardDir, "people1.plist");
        PropertyListParser.saveAsXML(root, sdFile);
    }

}
