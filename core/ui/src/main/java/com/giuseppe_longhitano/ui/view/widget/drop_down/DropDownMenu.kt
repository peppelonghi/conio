package com.giuseppe_longhitano.ui.view.widget.drop_down

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.giuseppe_longhitano.arch.event.UIEvent
import com.giuseppe_longhitano.ui.view.widget.drop_down.ui_model.DropDownModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T>DropDownMenu(modifier: Modifier = Modifier, items: List<DropDownModel<T>>, handleEvent: (UIEvent) -> Unit, title: String? = null) {
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(items.first().value) }
    Column( modifier= modifier) {
      if (title!=null)  Text(text = title)
        ExposedDropdownMenuBox(
            modifier = Modifier.fillMaxWidth(),
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            OutlinedTextField(
                value = selectedText,
                onValueChange = { selectedText = it },
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryNotEditable).fillMaxWidth()
            )
            ExposedDropdownMenu(
                modifier = Modifier.fillMaxWidth(),
                expanded = expanded,
                onDismissRequest = { expanded = false },
            ) {
                items.forEach { selectionOption ->
                    DropdownMenuItem(
                        text = { Text(selectionOption.value) },
                        onClick = {
                            selectedText = selectionOption.value
                            handleEvent.invoke(DropDownEvent(selectionOption.model))
                            expanded = false
                        },
                    )
                }
            }
        }
    }

}