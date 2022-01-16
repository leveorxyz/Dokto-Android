package com.toybethsystems.dokto.twilio.data.sdk

import com.twilio.video.RemoteParticipant
import com.twilio.video.StatsReport

data class RoomStats(
    val remoteParticipants: List<RemoteParticipant>,
    val localVideoTrackNames: Map<String, String>,
    val statsReports: List<StatsReport>? = null
)
