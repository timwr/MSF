
package com.metasploit.msf.fragments;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.metasploit.msf.R;
import com.metasploit.msf.model.ModuleOption;

import java.util.List;

public class ModuleOptionAdapter extends ArrayAdapter<ModuleOption> {

    private final int layoutResourceId;
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_BOOL = 1;
    private static final int TYPE_ENUM = 2;
    private static final int TYPE_ITEM = 3;
    private static final int TYPE_MAX_COUNT = 4;

    public ModuleOptionAdapter(Context context, int resource, List<ModuleOption> objects) {
        super(context, resource, objects);
        layoutResourceId = resource;
    }

    @Override
    public int getItemViewType(int position) {
        if (getItem(position).desc == null)
            return TYPE_HEADER;
        String type = getItem(position).type;
        if ("bool".equals(type)) {
            return TYPE_BOOL;
        } else if ("enum".equals(type)) {
            return TYPE_ENUM;
        }
        return TYPE_ITEM;
    }

    @Override
    public int getViewTypeCount() {
        return TYPE_MAX_COUNT;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        final int type = getItemViewType(position);
        final ModuleOption moduleOption = getItem(position);

        if (convertView == null) {
            holder = new ViewHolder();
            switch (type) {
                case TYPE_HEADER:
                    convertView = View.inflate(getContext(), android.R.layout.preference_category, null);
                    holder.textviewName = (TextView) convertView.findViewById(android.R.id.title);
                    break;
                default:
                    convertView = View.inflate(getContext(), R.layout.view_module_option, null);
                    holder.layoutContainer = (LinearLayout) convertView.findViewById(R.id.layout_container);
                    holder.textviewName = (TextView) convertView.findViewById(R.id.textview_name);
                    holder.textviewDescription = (TextView) convertView.findViewById(R.id.textview_description);
                    if (type == TYPE_BOOL) {
                        holder.checkboxBool = new CheckBox(getContext());
                        holder.layoutContainer.addView(holder.checkboxBool);
                    } else if (type == TYPE_ENUM) {
                        holder.spinnerEnum = new Spinner(getContext());
                        holder.layoutContainer.addView(holder.spinnerEnum);
                    } else {
                        holder.edittextValue = new EditText(getContext());
                        holder.layoutContainer.addView(holder.edittextValue);
                    }
                    break;
            }
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (type == TYPE_HEADER) {
            holder.textviewName.setText(moduleOption.name);
            return convertView;
        } else if (type == TYPE_BOOL) {
            holder.checkboxBool.setChecked(Boolean.TRUE.equals(moduleOption.value));
            holder.checkboxBool.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    moduleOption.value = new Boolean(isChecked);
                }
            });
        } else if (type == TYPE_ENUM) {
            ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, moduleOption.enums);
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
            holder.spinnerEnum.setAdapter(spinnerAdapter);
            holder.spinnerEnum.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                }
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        } else {
            if (moduleOption.value != null) {
                holder.edittextValue.setText(moduleOption.value.toString());
            } else {
                holder.edittextValue.setText("");
            }
        }

        holder.textviewName.setText(moduleOption.name);
        if (moduleOption.advanced) {
            holder.textviewName.setText(moduleOption.name + " (advanced)");
        }
        holder.textviewDescription.setText(moduleOption.desc);
        holder.layoutContainer.setVisibility(View.VISIBLE);

        return convertView;
    }

    private static class ViewHolder {
        LinearLayout layoutContainer;
        TextView textviewName;
        TextView textviewDescription;
        CheckBox checkboxBool;
        Spinner spinnerEnum;
        EditText edittextValue;
    }
}
