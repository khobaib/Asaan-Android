package com.techfiesta.asaan.activity;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.Toast;

import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.StoreMenuItemModifier;
import com.asaan.server.com.asaan.server.endpoint.storeendpoint.model.StoreMenuItemModifierGroup;
import com.techfiesta.asaan.R;
import com.techfiesta.asaan.fragment.MenuItemsFragment;
import com.techfiesta.asaan.utility.AsaanUtility;

@SuppressLint("UseSparseArrays")
public class MenuModifierActivity extends ListActivity {
	long selectedModGrpId, selectedModGrpMax, selectedModGrpMin;
	int selectedModGrpIndex;

	List<ModifierWithSelection> modifiersWithSelection;

	private static class ModifierWithSelection {
		StoreMenuItemModifier m;
		String title;
		boolean isSelected;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.menu_modifier);
		Button btnSave = (Button) this.findViewById(R.id.btn_save);

		Bundle bundle = this.getIntent().getExtras();
		selectedModGrpId = bundle.getLong(MenuItemsFragment.BUNDLE_KEY_MODIFIERGRP_ID);
		selectedModGrpIndex = bundle.getInt(MenuItemsFragment.BUNDLE_KEY_MODIFIERGRP_INDEX);
		ArrayList<Integer> modOrigSelections = this.getIntent().getIntegerArrayListExtra(
				PlaceOrderActivity.SELECTED_MODIFIERS);

		modifiersWithSelection = new ArrayList<ModifierWithSelection>();
		ArrayList<String> modTitles = new ArrayList<String>();
		StoreMenuItemModifierGroup mg = PlaceOrderActivity.menuItemModifiersAndGroups.getModifierGroups().get(
				selectedModGrpIndex);
		selectedModGrpMax = mg.getModifierGroupMaximum();
		selectedModGrpMin = mg.getModifierGroupMinimum();
		for (StoreMenuItemModifier m : PlaceOrderActivity.menuItemModifiersAndGroups.getModifiers()) {
			if (m.getModifierGroupPOSId().longValue() == selectedModGrpId) {
				// selectedModGrpMax = m.getModifierGroupMaximum();
				// selectedModGrpMin = m.getModifierGroupMinimum();

				ModifierWithSelection modifierWithSelection = new ModifierWithSelection();
				modifierWithSelection.m = m;
				String title;
				if (m.getPrice() > 0)
					title = m.getShortDescription() + " (" + AsaanUtility.formatCentsToCurrency(m.getPrice()) + ")";
				else
					title = m.getShortDescription();
				modifierWithSelection.isSelected = false;
				if (modOrigSelections == null)
					continue;
				for (Integer selectedModPOSId : modOrigSelections)
					if (selectedModPOSId.longValue() == m.getModifierPOSId().longValue()) {
						modifierWithSelection.isSelected = true;
						break;
					}
				modTitles.add(title);
				modifierWithSelection.title = title;
				modifiersWithSelection.add(modifierWithSelection);
			}
		}

		final ListView listView = getListView();

		if (selectedModGrpMax > 1)
			listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		else
			listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

		ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked,
				modTitles);
		setListAdapter(myAdapter);

		int i = 0;
		for (ModifierWithSelection modifierWithSelection : modifiersWithSelection) {
			if (modifierWithSelection.isSelected == true)
				listView.setItemChecked(i, true);
			i++;
		}

		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				if (listView.getChoiceMode() == ListView.CHOICE_MODE_SINGLE) {
					int i = 0;
					for (ModifierWithSelection modifierWithSelection : modifiersWithSelection) {
						if (i != position && modifierWithSelection.isSelected == true)
							modifierWithSelection.isSelected = false;
						if (i == position && modifierWithSelection.isSelected == true)
							modifierWithSelection.isSelected = false;
						if (i == position && modifierWithSelection.isSelected == false)
							modifierWithSelection.isSelected = true;
						i++;
					}
				} else {
					CheckedTextView ctv = (CheckedTextView) view;
					ModifierWithSelection modifierWithSelection = modifiersWithSelection.get(position);
					if (ctv.isChecked() == true)
						modifierWithSelection.isSelected = true;
					else
						modifierWithSelection.isSelected = false;
				}
			}
		});

		btnSave.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				long selCnt = 0;
				for (ModifierWithSelection modifierWithSelection : modifiersWithSelection)
					if (modifierWithSelection.isSelected == true)
						selCnt++;
				if (selectedModGrpMax > 0 && selectedModGrpMax < selCnt) {
					Toast.makeText(getApplication(), "Please select only " + selectedModGrpMax + " options",
							Toast.LENGTH_SHORT).show();
					return;
				}

				if (selectedModGrpMin > 0 && selectedModGrpMin > selCnt) {
					Toast.makeText(getApplication(), "Please select at least " + selectedModGrpMin + " options",
							Toast.LENGTH_SHORT).show();
					return;
				}

				String strDesc = " ";
				long price = 0;

				ArrayList<Integer> finalSelections = new ArrayList<Integer>();
				for (ModifierWithSelection modifierWithSelection : modifiersWithSelection) {
					if (modifierWithSelection.isSelected == false)
						continue;
					StoreMenuItemModifier m = modifierWithSelection.m;
					finalSelections.add(m.getModifierPOSId().intValue());
					if (m.getPrice() > 0) {
						price += m.getPrice();
						if (strDesc.contentEquals(" "))
							strDesc = m.getLongDescription() + " (" + AsaanUtility.formatCentsToCurrency(m.getPrice())
									+ ")";
						else
							strDesc += ", " + m.getLongDescription() + " ("
									+ AsaanUtility.formatCentsToCurrency(m.getPrice()) + ")";

					} else {
						if (strDesc.contentEquals(" "))
							strDesc = m.getLongDescription();
						else
							strDesc += ", " + m.getLongDescription();
					}
				}

				Intent intent = new Intent();

				intent.putIntegerArrayListExtra(PlaceOrderActivity.SELECTED_MODIFIERS, finalSelections);
				intent.putExtra(PlaceOrderActivity.SELECTED_MODIFIERS_DESC, strDesc);
				intent.putExtra(PlaceOrderActivity.SELECTED_MODIFIERS_PRICE, price);
				setResult(RESULT_OK, intent);
				finish();
			}
		});
	}
}
