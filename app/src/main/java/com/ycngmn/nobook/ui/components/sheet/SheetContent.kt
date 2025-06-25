package com.ycngmn.nobook.ui.components.sheet

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.net.toUri
import com.ycngmn.nobook.R
import com.ycngmn.nobook.ui.NobookViewModel
import com.ycngmn.nobook.utils.isAutoDesktop

@Composable
fun SheetContent(
    viewModel: NobookViewModel,
    onRestart: () -> Unit,
    onClose: () -> Unit
) {
    val context = LocalContext.current
    val isOpenDialog = remember { mutableStateOf(false) }

    val removeAds = viewModel.removeAds.collectAsState()
    val enableDownloadContent = viewModel.enableDownloadContent.collectAsState()
    val desktopLayout = viewModel.desktopLayout.collectAsState()
    val immersiveMode = viewModel.immersiveMode.collectAsState()
    val stickyNavbar = viewModel.stickyNavbar.collectAsState()
    val pinchToZoom = viewModel.pinchToZoom.collectAsState()
    val amoledBlack = viewModel.amoledBlack.collectAsState()


    Box(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.background)
            .verticalScroll(state = rememberScrollState())
    ) {
        Column(
            modifier = Modifier
                .padding(vertical = 12.dp)
        ) {
            SheetItem(
                icon = R.drawable.adblock_24px,
                title = stringResource(R.string.remove_ads_title),
                isActive = removeAds.value
            ) {
                viewModel.setRemoveAds(!removeAds.value)
            }

            SheetItem(
                icon = R.drawable.download_24px,
                title = stringResource(R.string.download_content_title),
                isActive = enableDownloadContent.value
            ) {
                viewModel.setEnableDownloadContent(!enableDownloadContent.value)
            }

            SheetItem(
                icon = R.drawable.pinch_zoom_out_24px,
                title = stringResource(R.string.pinch_to_zoom_title),
                isActive = pinchToZoom.value
            ) {
                viewModel.setPinchToZoom(!pinchToZoom.value)
            }

            val isAutoDesktop = isAutoDesktop()
            SheetItem(
                icon = R.drawable.computer_24px,
                title = stringResource(R.string.desktop_layout_title),
                isActive = desktopLayout.value
            ) {
                if (!isAutoDesktop) viewModel.setDesktopLayout(!desktopLayout.value)
            }

            SheetItem(
                icon = R.drawable.immersive_mode_24px,
                title = stringResource(R.string.immersive_mode_title),
                isActive = immersiveMode.value
            ) {
                viewModel.setImmersiveMode(!immersiveMode.value)
            }

            SheetItem(
                icon = R.drawable.sticky_navbar_24px,
                title = stringResource(R.string.sticky_navbar_title),
                isActive = stickyNavbar.value
            ) {
                viewModel.setStickyNavbar(!stickyNavbar.value)
            }

            SheetItem(
                icon = R.drawable.amoled_black_24px,
                title = stringResource(R.string.amoled_black_title),
                isActive = amoledBlack.value,
            ) {
                viewModel.setAmoledBlack(!amoledBlack.value)
            }

            SheetItem(
                icon = R.drawable.customize_feed_24px,
                title = stringResource(R.string.customize_feed_title),
                tailIcon = R.drawable.chevron_forward_24px

            ) {
                isOpenDialog.value = true
            }

            if (isOpenDialog.value) HideOptionsDialog(viewModel) {
                isOpenDialog.value = false
            }

            SheetItem(
                icon = R.drawable.github_mark_white,
                title = stringResource(R.string.follow_at_github),
                tailIcon = R.drawable.arrow_outward_24px
            ) {
                val intent = Intent(Intent.ACTION_VIEW, "https://github.com/ycngmn/nobook".toUri())
                context.startActivity(intent)
            }

            Row(
                modifier = Modifier.align(Alignment.CenterHorizontally)
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Card  (
                    modifier = Modifier.clickable { onRestart() },
                    shape = RoundedCornerShape(6.dp),
                    elevation = CardDefaults.cardElevation(2.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.background
                    )
                ) {
                    Text(
                        text = stringResource(R.string.apply_immediately),
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(5.dp)
                    )
                }

                VerticalDivider(Modifier.height(30.dp), color = Color.Gray, thickness = 2.dp)

                Card  (
                    shape = RoundedCornerShape(6.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.background
                    )
                ) {

                    Text(
                        text = stringResource(R.string.close_menu),
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(5.dp)
                            .clickable { onClose() }
                    )
                }
            }
        }
    }
}

@Composable
private fun HideOptionsDialog(viewModel: NobookViewModel, onClose: () -> Unit) {

    val hideSuggested = viewModel.hideSuggested.collectAsState()
    val hideReels = viewModel.hideReels.collectAsState()
    val hideStories = viewModel.hideStories.collectAsState()
    val hidePeopleYouMayKnow = viewModel.hidePeopleYouMayKnow.collectAsState()
    val hideGroups = viewModel.hideGroups.collectAsState()

    Dialog(
        onDismissRequest = { onClose() }
    ) {
        Card(
            shape = RoundedCornerShape(10.dp)
        ) {
            SheetItem(
                icon = R.drawable.public_off_24px,
                title = stringResource(R.string.hide_suggested_title),
                isActive = hideSuggested.value

            ) {
                viewModel.setHideSuggested(!hideSuggested.value)
            }

            SheetItem(
                icon = R.drawable.movie_off_24px,
                title = stringResource(R.string.hide_reels_title),
                isActive = hideReels.value
            ) {
                viewModel.setHideReels(!hideReels.value)
            }

            SheetItem(
                icon = R.drawable.landscape_2_off_24px,
                title = stringResource(R.string.hide_stories_title),
                isActive = hideStories.value
            ) {
                viewModel.setHideStories(!hideStories.value)
            }

            if (viewModel.desktopLayout.collectAsState().value) return@Card

            SheetItem(
                icon = R.drawable.frame_person_off_24px,
                title = stringResource(R.string.hide_people_you_may_know_title),
                isActive = hidePeopleYouMayKnow.value
            ) {
                viewModel.setHidePeopleYouMayKnow(!hidePeopleYouMayKnow.value)
            }

            SheetItem(
                icon = R.drawable.group_off_24px,
                title = stringResource(R.string.hide_groups_title),
                isActive = hideGroups.value
            ) {
                viewModel.setHideGroups(!hideGroups.value)
            }
        }
    }
}
