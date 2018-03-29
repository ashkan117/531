package com.example.ashkan.a531;

import android.content.ContentValues;
import android.net.Uri;
import android.test.ProviderTestCase2;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Created by Ashkan on 2/15/2018.
 * Had to add
 *  testOptions {
 unitTests.returnDefaultValues = true
 }
 since it was giving a mock error???
 */
@RunWith(JUnit4.class)
public class ContentProviderTest extends ProviderTestCase2<OneRepMaxContentProvider> {
//    /**
//     * Constructor.
//     *
//     * @param providerClass     The class name of the provider under test
//     * @param providerAuthority The provider's authority string
//     */
//    public ContentProviderTest(Class<OneRepMaxContentProvider> providerClass, String providerAuthority) {
//        super(providerClass, providerAuthority);
//    }
    private ContentValues mContentValues = new ContentValues();

    public ContentProviderTest() {
        super(OneRepMaxContentProvider.class, String.valueOf(ContractClass.OneRepMaxEntry.CONTENT_URI));
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    @Test
    public void testInsertOneRepMax(){
        setContext(getMockContext());
        mContentValues = new ContentValues();
        ContentValues values = new ContentValues();
        mContentValues.put(ContractClass.OneRepMaxEntry.COLUMN_NAME_BENCH_PRESS,100);
        values.put(ContractClass.OneRepMaxEntry.COLUMN_NAME_SQUAT,125);
        values.put(ContractClass.OneRepMaxEntry.COLUMN_NAME_DEADLIFT,150);
        values.put(ContractClass.OneRepMaxEntry.COLUMN_NAME_OHP,175);
        Uri uri = getMockContentResolver().insert(ContractClass.OneRepMaxEntry.CONTENT_URI,values);
        assertNotNull(uri);
    }
}
