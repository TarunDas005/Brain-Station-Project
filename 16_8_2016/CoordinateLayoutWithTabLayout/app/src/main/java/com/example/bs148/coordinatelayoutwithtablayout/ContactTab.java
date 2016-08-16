package com.example.bs148.coordinatelayoutwithtablayout;


import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContactTab extends Fragment {
    ProgressDialog pd;
    ArrayList<ContactsInformation> contacts;
    private RecyclerView recyclerView;
    public ContactTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_contact_tab, container, false);
        recyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_contact_tab, container, false);
        return recyclerView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        contacts=new ArrayList<ContactsInformation>();
//        contacts=displayContacts();
//        //Toast.makeText(getContext(),contacts.size()+"",Toast.LENGTH_LONG).show();
//        recyclerView.setAdapter(new ContactsAdapter(contacts));
        new DownloadFilesTask().execute();
    }


    private ArrayList<ContactsInformation> displayContacts() {
        ArrayList<ContactsInformation> contactsInformations=new ArrayList<ContactsInformation>();

        ContentResolver contentResolver = getActivity().getContentResolver();
        Cursor cursor=contentResolver.query(ContactsContract.Contacts.CONTENT_URI,null,null,null,null);
        if(cursor!=null  && cursor.getCount()>0){
            cursor.moveToFirst();
            do{
                String id=cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                String name=cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                int hasPhoneNumber=Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)));
                if(hasPhoneNumber>0){
                    Cursor phoneCursor=contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,ContactsContract.CommonDataKinds.Phone.CONTACT_ID+"=?",new String[]{id},null);
                    if(phoneCursor!=null && phoneCursor.getCount()>0){
                        phoneCursor.moveToFirst();
                        do{
                            String phoneNumber=phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            //Toast.makeText(getActivity(),"Name: "+name+" Number: "+phoneNumber,Toast.LENGTH_LONG).show();
                            ContactsInformation information=new ContactsInformation(name,phoneNumber);
                            contactsInformations.add(information);
                        }while (phoneCursor.moveToNext());
                    }
                    phoneCursor.close();
                }
            }while (cursor.moveToNext());
        }
        cursor.close();
        return contactsInformations;
    }


    private class DownloadFilesTask extends AsyncTask<Void, Void, ArrayList<ContactsInformation>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = ProgressDialog.show(getActivity(),
                    "Loading..", "Please Wait", true, false);
        }

        protected void onPostExecute(ArrayList<ContactsInformation> contacts) {
            pd.dismiss();
            int x=contacts.size();
            recyclerView.setAdapter(new ContactsAdapter(contacts));
        }

        @Override
        protected ArrayList<ContactsInformation> doInBackground(Void... params) {
            ArrayList<ContactsInformation> contactsInformations=displayContacts();
            return contactsInformations;
        }
    }

}
