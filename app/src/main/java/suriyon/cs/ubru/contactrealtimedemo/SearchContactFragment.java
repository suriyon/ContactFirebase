package suriyon.cs.ubru.contactrealtimedemo;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import suriyon.cs.ubru.contactrealtimedemo.adapter.ContactAdapter;
import suriyon.cs.ubru.contactrealtimedemo.model.Contact;


public class SearchContactFragment extends Fragment {
    private RecyclerView rcvContact;
    private List<Contact> contacts;
//    private List<Contact> contactsRead;
    private String[] arrayContacts;
    private ContactAdapter adapter;
    private DatabaseReference refContact;
    private AutoCompleteTextView completeTextView;
//    private EditText edtSearch;
    private Button btnSearch;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_contact, container, false);
        contacts = new ArrayList<>();
//        contactsRead = new ArrayList<>();
        refContact = FirebaseDatabase.getInstance().getReference("contacts");
        arrayContacts = null;
        refContact.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //contactsRead.clear();
                contacts.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Contact contact = snapshot.getValue(Contact.class);
                    contacts.add(contact);
                }
                arrayContacts = new String[contacts.size()];

                for(int i=0; i<arrayContacts.length; i++){
                    arrayContacts[i] = contacts.get(i).getName();
                }
                Log.d("Array", contacts.get(0).getName() + "");
                ArrayAdapter<String> adapterAuto = new ArrayAdapter<String>(getContext(), android.R.layout.select_dialog_item, arrayContacts);
                completeTextView.setThreshold(1);
                completeTextView.setAdapter(adapterAuto);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        matchView(view);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = completeTextView.getText().toString().trim();
                Query query = refContact.orderByChild("name").equalTo(name);

                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        contacts.clear();

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Contact contact = snapshot.getValue(Contact.class);

                            contacts.add(contact);
                        }

                        adapter = new ContactAdapter(getContext(), contacts);
                        rcvContact.setAdapter(adapter);
                        rcvContact.setLayoutManager(new LinearLayoutManager(getContext()));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                completeTextView.setText("");
            }
        });
        return view;
    }

    private void matchView(View view) {
        btnSearch = view.findViewById(R.id.btn_search);
        //edtSearch = view.findViewById(R.id.edt_search);
        rcvContact = view.findViewById(R.id.rcv_search_contact);
        completeTextView = view.findViewById(R.id.edt_search);
    }
}