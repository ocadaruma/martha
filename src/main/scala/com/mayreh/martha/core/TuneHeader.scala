package com.mayreh.martha.core

case class TuneHeader(
  reference: Option[Metadata.Reference],
  tuneTitle: Option[Metadata.TuneTitle],
  composer: Option[Metadata.Composer],
  meter: Metadata.Meter,
  unitNoteLength: Metadata.UnitNoteLength,
  tempo: Metadata.Tempo,
  key: Metadata.Key,
  voiceHeaders: Seq[Metadata.VoiceHeader]
)
