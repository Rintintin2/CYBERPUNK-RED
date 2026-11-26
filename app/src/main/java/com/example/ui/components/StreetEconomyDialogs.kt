package com.example.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.core.rules.GeneratedNightMarket
import com.example.core.rules.HustleRollResult
import com.example.core.rules.NightMarketOffer
import com.example.core.rules.OfficialEconomyAndProgression
import com.example.core.rules.RedRole
import com.example.ui.theme.CyberAmber
import com.example.ui.theme.CyberCyan
import com.example.ui.theme.CyberEmerald
import com.example.ui.theme.CyberSurfaceElevated
import com.example.ui.theme.RedlineCrimson

@Composable
fun NightMarketGeneratorDialog(
    market: GeneratedNightMarket?,
    eurodollars: Int,
    onGenerate: () -> Unit,
    onBuyOffer: (NightMarketOffer) -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Mercado Noturno Aleatório (d6 & d100)") },
        text = {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Saldo atual: $eurodollars eb • Sorteia 2 categorias distintas (1d6) e 1d10 itens cada (p. 338).",
                    style = MaterialTheme.typography.bodySmall,
                    color = CyberAmber
                )
                Button(
                    onClick = onGenerate,
                    colors = ButtonDefaults.buttonColors(containerColor = CyberAmber),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("roll_night_market_button")
                ) {
                    Text(
                        text = if (market == null) "Sortear Mercado Noturno" else "Gerar Novo Mercado",
                        color = MaterialTheme.colorScheme.onTertiary
                    )
                }
                if (market != null) {
                    Text(
                        text = "Bancas Ativas: ${market.categories.joinToString(" + ") { it.labelPt }}",
                        style = MaterialTheme.typography.labelLarge,
                        color = CyberCyan
                    )
                    market.offers.forEach { offer ->
                        CyberPanel(containerColor = CyberSurfaceElevated) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(offer.labelPt, style = MaterialTheme.typography.titleSmall)
                                    Text(
                                        text = "${offer.category.labelPt} • d100=${offer.d100Roll} • ${offer.costEb} eb",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = CyberAmber
                                    )
                                }
                                Spacer(Modifier.width(8.dp))
                                OutlinedButton(
                                    onClick = { onBuyOffer(offer) },
                                    enabled = eurodollars >= offer.costEb
                                ) {
                                    Text("Comprar")
                                }
                            }
                        }
                    }
                }
            }
        },
        confirmButton = { TextButton(onClick = onDismiss) { Text("Fechar") } }
    )
}

@Composable
fun HustleAndLifestyleDialog(
    role: RedRole,
    roleRank: Int,
    currentLifestyle: String,
    rentDueInDays: Int,
    eurodollars: Int,
    lastHustle: HustleRollResult?,
    onRunHustle: (advanceSevenDays: Boolean) -> Unit,
    onPayLifestyleOrHousing: (description: String, costEb: Int, newLifestyleLabel: String?, resetRentCycle: Boolean) -> Unit,
    onDismiss: () -> Unit
) {
    var advanceSevenDays by remember { mutableStateOf(true) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Hustle & Custo de Vida") },
        text = {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                CyberPanel(accentColor = CyberCyan) {
                    Text(
                        text = "HUSTLE SEMANAL — ${role.displayNamePt} (Rank $roleRank)",
                        style = MaterialTheme.typography.labelLarge,
                        color = CyberCyan
                    )
                    Spacer(Modifier.height(4.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(checked = advanceSevenDays, onCheckedChange = { advanceSevenDays = it })
                        Text("Avançar 7 dias no relógio e prazo de aluguel", style = MaterialTheme.typography.bodySmall)
                    }
                    Button(
                        onClick = { onRunHustle(advanceSevenDays) },
                        colors = ButtonDefaults.buttonColors(containerColor = CyberCyan),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("run_hustle_button")
                    ) {
                        Text("Rolar Hustle (1d6)", color = MaterialTheme.colorScheme.onSecondary)
                    }
                    if (lastHustle != null) {
                        Spacer(Modifier.height(6.dp))
                        Text(
                            text = "Último Hustle [1d6=${lastHustle.roll1d6}]: ${lastHustle.activityPt} (+${lastHustle.earnedEb} eb)",
                            style = MaterialTheme.typography.bodySmall,
                            color = CyberEmerald,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Text(
                    text = "Estilo atual: $currentLifestyle • Aluguel vence em $rentDueInDays dia(s) • Saldo: $eurodollars eb",
                    style = MaterialTheme.typography.labelMedium,
                    color = CyberAmber
                )

                Text("Pagar Estilo de Vida (Mensal)", style = MaterialTheme.typography.labelLarge)
                OfficialEconomyAndProgression.lifestyles.forEach { ls ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 2.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("${ls.namePt} (${ls.monthlyCostEb} eb)", style = MaterialTheme.typography.bodyMedium)
                            Text(ls.descriptionPt, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        OutlinedButton(
                            onClick = { onPayLifestyleOrHousing("Estilo ${ls.namePt}", ls.monthlyCostEb, ls.namePt, false) },
                            enabled = eurodollars >= ls.monthlyCostEb
                        ) { Text("Pagar") }
                    }
                }

                Text("Pagar Aluguel / Moradia (Reinicia 30d)", style = MaterialTheme.typography.labelLarge)
                OfficialEconomyAndProgression.housings.filter { it.monthlyRentEb != null }.forEach { h ->
                    val rent = h.monthlyRentEb ?: 0
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 2.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("${h.namePt} ($rent eb/mês)", style = MaterialTheme.typography.bodyMedium)
                            Text(h.notesPt, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        OutlinedButton(
                            onClick = { onPayLifestyleOrHousing("Aluguel: ${h.namePt}", rent, null, true) },
                            enabled = eurodollars >= rent
                        ) { Text("Alugar") }
                    }
                }
            }
        },
        confirmButton = { TextButton(onClick = onDismiss) { Text("Fechar") } }
    )
}
