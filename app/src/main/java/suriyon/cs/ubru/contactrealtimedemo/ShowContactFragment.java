package suriyon.cs.ubru.contactrealtimedemo;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import suriyon.cs.ubru.contactrealtimedemo.adapter.ContactAdapter;
import suriyon.cs.ubru.contactrealtimedemo.model.Contact;


public class ShowContactFragment extends Fragment {
    private RecyclerView rcvContact;
    private DatabaseReference refContact;
    private List<Contact> contacts;
    private ContactAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        refContact = FirebaseDatabase.getInstance().getReference("contacts");
        contacts = new ArrayList<>();
        View view = inflater.inflate(R.layout.fragment_show_contact, container, false);
        matchView(view);

        refContact.addValueEventListener(new ValueEventListener() {
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

        return view;
    }

    private void matchView(View view) {
        rcvContact = view.findViewById(R.id.rcv_contact);
    }
}