package suriyon.cs.ubru.contactrealtimedemo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    private ContactAdapter adapter;
    private DatabaseReference refContact;

    private EditText edtSearch;
    private Button btnSearch;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_contact, container, false);
        contacts = new ArrayList<>();
        refContact = FirebaseDatabase.getInstance().getReference("contacts");

        matchView(view);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edtSearch.getText().toString().trim();
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

                edtSearch.setText("");
            }
        });
        return view;
    }

    private void matchView(View view) {
        btnSearch = view.findViewById(R.id.btn_search);
        edtSearch = view.findViewById(R.id.edt_search);
        rcvContact = view.findViewById(R.id.rcv_search_contact);
    }
}