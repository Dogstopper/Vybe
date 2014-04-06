package bitcamp.vybe;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by hunter on 4/6/14.
 */
public class CustomListAdapter extends ArrayAdapter<CustomListAdapter.Contact> {
    public static class Contact {
        public String name;
        public int imageResource;
        public String phone;
    }
    private final Context context;
    private final Contact[] values;

    public CustomListAdapter(Context context, Contact[] values) {
        super(context, R.layout.unit, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.unit, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.firstLine);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        textView.setText(values[position].name);
        imageView.setImageResource(values[position].imageResource);

        return rowView;
    }
}
