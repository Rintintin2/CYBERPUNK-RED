package com.example.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.core.rules.RedStat
import com.example.data.local.entity.CharacterSkillEntity
import com.example.ui.theme.CyberCyan
import com.example.ui.theme.CyberSurface
import com.example.ui.theme.RedlineCrimson

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun DiceRollerModal(
    initialStat: RedStat,
    initialSkillName: String,
    initialSkillLevel: Int,
    maxAvailableLuck: Int,
    woundPenalty: Int,
    skills: List<CharacterSkillEntity>,
    onDismiss: () -> Unit,
    onExecuteRoll: (
        stat: RedStat,
        skillName: String,
        skillLevel: Int,
        luckSpent: Int,
        modifier: Int,
        dv: Int
    ) -> Unit
) {
    var selectedStat by remember { mutableStateOf(initialStat) }
    var skillName by remember { mutableStateOf(initialSkillName) }
    var skillLevel by remember { mutableIntStateOf(initialSkillLevel) }
    var luckSpent by remember { mutableIntStateOf(0) }
    var modifier by remember { mutableIntStateOf(0) }
    var difficultyValue by remember { mutableIntStateOf(13) }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = CyberSurface
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SectionHeader(
                title = "Teste Determinístico (1d10)",
                subtitle = "10 explode (+1d10) • 1 implode (-1d10) • Supera DV",
                accentColor = CyberCyan
            )

            if (skills.isNotEmpty()) {
                Text(
                    text = "Atalho de Perícia",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    skills.take(6).forEach { skill ->
                        FilterChip(
                            selected = skillName == skill.name,
                            onClick = {
                                selectedStat = skill.stat
                                skillName = skill.name
                                skillLevel = skill.level
                            },
                            label = { Text("${skill.name} (${skill.stat.code}+${skill.level})") }
                        )
                    }
                }
            }

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                RedStat.entries.forEach { stat ->
                    FilterChip(
                        selected = selectedStat == stat,
                        onClick = { selectedStat = stat },
                        label = { Text(stat.code) }
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedTextField(
                    value = skillName,
                    onValueChange = { skillName = it },
                    label = { Text("Perícia / Ação") },
                    singleLine = true,
                    modifier = Modifier.weight(1.6f)
                )
                OutlinedTextField(
                    value = skillLevel.toString(),
                    onValueChange = { v -> skillLevel = v.toIntOrNull()?.coerceIn(0, 10) ?: 0 },
                    label = { Text("Nível") },
                    singleLine = true,
                    modifier = Modifier.weight(0.7f)
                )
            }

            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Dificuldade (DV)",
                        style = MaterialTheme.typography.labelMedium
                    )
                    Text(
                        text = "DV $difficultyValue",
                        style = MaterialTheme.typography.labelLarge,
                        color = RedlineCrimson
                    )
                }
                Slider(
                    value = difficultyValue.toFloat(),
                    onValueChange = { difficultyValue = it.toInt() },
                    valueRange = 9f..29f,
                    steps = 19
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Sorte gasta (disp: $maxAvailableLuck): $luckSpent",
                    style = MaterialTheme.typography.bodyMedium
                )
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    FilterChip(
                        selected = false,
                        onClick = { luckSpent = (luckSpent - 1).coerceAtLeast(0) },
                        label = { Text("-1 SOR") }
                    )
                    FilterChip(
                        selected = false,
                        onClick = { luckSpent = (luckSpent + 1).coerceAtMost(maxAvailableLuck) },
                        label = { Text("+1 SOR") }
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Modificador manual: ${if (modifier >= 0) "+$modifier" else modifier} (Ferim: $woundPenalty)",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    FilterChip(
                        selected = false,
                        onClick = { modifier = (modifier - 1).coerceAtLeast(-8) },
                        label = { Text("-1") }
                    )
                    FilterChip(
                        selected = false,
                        onClick = { modifier = (modifier + 1).coerceAtMost(8) },
                        label = { Text("+1") }
                    )
                }
            }

            Button(
                onClick = {
                    onExecuteRoll(
                        selectedStat,
                        skillName.ifBlank { "Teste" },
                        skillLevel,
                        luckSpent,
                        modifier,
                        difficultyValue
                    )
                    onDismiss()
                },
                colors = ButtonDefaults.buttonColors(containerColor = RedlineCrimson),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .testTag("execute_roll_button")
            ) {
                Icon(imageVector = Icons.Filled.Casino, contentDescription = null)
                Spacer(modifier = Modifier.padding(horizontal = 4.dp))
                Text("ROLAR 1D10 DETERMINÍSTICO")
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
