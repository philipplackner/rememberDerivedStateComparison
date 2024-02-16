package com.plcoding.rememberderivedstatecomparison

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.plcoding.rememberderivedstatecomparison.ui.theme.RememberDerivedStateComparisonTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RememberDerivedStateComparisonTheme {
                val state = rememberLazyListState()

                var isEnabled by remember {
                    mutableStateOf(true)
                }

                Scaffold(
                    modifier = Modifier
                        .fillMaxSize(),
                    floatingActionButton = {
                        ScrollToTopButton(
                            state = state,
                            isEnabled = isEnabled
                        )
                    }
                ) { padding ->
                    LazyColumn(
                        state = state,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding),
                        contentPadding = PaddingValues(16.dp)
                    ) {
                        items(100) {
                            Text(
                                text = "Item $it",
                                modifier = Modifier
                                    .padding(16.dp)
                                    .clickable {
                                        isEnabled = false
                                    }
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun ScrollToTopButton(
    state: LazyListState,
    isEnabled: Boolean
) {
    val scope = rememberCoroutineScope()

    val showScrollToTopButton by remember(isEnabled) {
        derivedStateOf {
            state.firstVisibleItemIndex >= 5 && isEnabled
        }
    }

//    val showScrollToTopButton = remember(state.firstVisibleItemIndex) {
//        state.firstVisibleItemIndex >= 5
//    }

    if(showScrollToTopButton) {
        FloatingActionButton(onClick = {
            scope.launch {
                state.animateScrollToItem(0)
            }
        }) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowUp,
                contentDescription = null
            )
        }
    }
}