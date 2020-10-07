package suriyon.cs.ubru.contactrealtimedemo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import suriyon.cs.ubru.contactrealtimedemo.model.Contact;


public class AddContactFragment extends Fragment {
    private EditText edtName, edtMobile;
    private Button btnAdd;
    private DatabaseReference refContact;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        refContact = FirebaseDatabase.getInstance().getReference("contacts");
        View view = inflater.inflate(R.layout.fragment_add_contact, container, false);
        matchView(view);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edtName.getText().toString().trim();
                String mobile = edtMobile.getText().toString().trim();

                if(name.isEmpty() || mobile.isEmpty()){
                    Toast.makeText(getContext(), "Please full fill data before!", Toast.LENGTH_SHORT).show();
                }else {
                    String id = refContact.push().getKey();
                    Contact contact = new Contact(id, name, mobile);

                    refContact.child(id).setValue(contact);

                    clearData();
                }
            }
        });

        return view;
    }

    private void clearData() {
        edtName.setText("");
        edtMobile.setText("");
        edtName.requestFocus();
    }

    private void matchView(View view) {
        edtName = view.findViewById(R.id.edt_add_name);
        edtMobile = view.findViewById(R.id.edt_add_mobile);
        btnAdd = view.findViewById(R.id.btn_add_contact);
    }
}