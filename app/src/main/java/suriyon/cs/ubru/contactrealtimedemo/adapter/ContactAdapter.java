package suriyon.cs.ubru.contactrealtimedemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import suriyon.cs.ubru.contactrealtimedemo.R;
import suriyon.cs.ubru.contactrealtimedemo.model.Contact;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {
    private Context context;
    private List<Contact> contacts;

    public ContactAdapter(Context context, List<Contact> contacts){
        this.context = context;
        this.contacts = contacts;
    }
    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_contact, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        holder.tvContactName.setText(contacts.get(position).getName());
        holder.tvContactMobile.setText(contacts.get(position).getMobile());
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder {
        TextView tvContactName, tvContactMobile;
        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            tvContactName = itemView.findViewById(R.id.tv_contact_name);
            tvContactMobile = itemView.findViewById(R.id.tv_contact_mobile);
        }
    }
}
