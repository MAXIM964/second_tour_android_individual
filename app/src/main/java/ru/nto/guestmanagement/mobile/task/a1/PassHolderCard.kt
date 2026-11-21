package ru.nto.guestmanagement.mobile.task.a1

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import ru.nto.guestmanagement.mobile.model.PassDetails
import ru.nto.guestmanagement.mobile.ui.TestIds
import java.time.format.DateTimeFormatter

@Composable
fun PassHolderCard(
    pass: PassDetails,
    modifier: Modifier = Modifier
) {
    val visitor = pass.visitor
    val dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")

    val fromDate = pass.validFrom.format(dateFormatter)
    val fromTime = pass.validFrom.format(timeFormatter)
    val toDate = pass.validTo.format(dateFormatter)
    val toTime = pass.validTo.format(timeFormatter)

    val validPeriodText = if (fromDate == toDate) {
        "Действует $fromDate, $fromTime–$toTime"
    } else {
        "Действует с $fromDate $fromTime по $toDate $toTime"
    }

    val limitText = formatEntriesLimit(pass.maxEntries)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = visitor.fullName,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.testTag(TestIds.PASS_HOLDER)
        )

        if (!visitor.organization.isNullOrBlank()) {
            Text(
                text = visitor.organization!!,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.testTag(TestIds.PASS_ORGANIZATION)
            )
        }

        Text(
            text = validPeriodText,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.testTag(TestIds.PASS_VALID_PERIOD)
        )

        Text(
            text = limitText,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.testTag(TestIds.PASS_ENTRIES_LIMIT)
        )
    }
}

private fun formatEntriesLimit(maxEntries: Int): String {
    if (maxEntries == 1) {
        return "Разовый пропуск"
    }
    val remainder100 = maxEntries % 100
    val remainder10 = maxEntries % 10

    val form = when {
        remainder100 in 11..14 -> "проходов"
        remainder10 == 1 -> "проход"
        remainder10 in 2..4 -> "прохода"
        else -> "проходов"
    }
    return "Лимит: $maxEntries $form"
}