# Hardware reference

The extended profile uses W65C02S, W65C22S, a 16×2 HD44780-compatible module, and W65C51-family ACIA. Canonical windows are listed below. See the [pinned serial schematic and decoder reference](extended-board-decoder.md) for physical selects, IRQ routing, mirrored register ranges and reference vectors.

| Range | Role |
|---|---|
| `$0000–$3FFF` | 16 KiB RAM, including zero page and stack |
| `$5000–$5003` | ACIA data, status, command, control |
| `$6000–$600F` | VIA offsets 0–F, including the requested initial 0–4 |
| `$8000–$FFFF` | 32 KiB ROM |
| `$FFFA–$FFFF` | Little-endian NMI, reset, IRQ/BRK vectors in ROM |

The proposed expanded profile has RAM at `$0000–$7FDF`, ACIA at `$7FE0–$7FE3`, an unmapped gap at `$7FE4–$7FEF`, VIA at `$7FF0–$7FFF`, and the same ROM. This synthetic profile uses exact decoding without mirrors. Software with hard-coded original I/O addresses must be reassembled or adapted. Compare programs built with profile-specific symbols.

Profiles define 16-bit region and image bounds, base addresses, decoding masks and aliases, ROM coverage, initialization, interrupt routing and wiring presets. Reject overlap between selected devices across the full address space, reporting the conflicting addresses. The extended profile uses a fixed `$FF` for open-bus reads and drops open-bus writes. Its reserved `$7000–$7FFF` range faults on CPU access because the physical board selects both I/O chips there.

Use the default 8-bit LCD wiring and a selectable 4-bit wiring preset derived from the reference examples. Test LCD timing against the chosen controller data, including initialization, clear/home, data reads, and busy polling. Keep chip revision choices, ACIA TX-status behavior and modem wiring explicit.

[Ben Eater schematics and example programs](https://eater.net/6502) · [WDC chip datasheets](https://www.westerndesigncenter.com/wdc/downloads.php) · [Hitachi HD44780U datasheet](https://cdn-shop.adafruit.com/datasheets/HD44780.pdf) · [Ben Eater WozMon](https://gist.github.com/beneater/8136c8b7f2fd95ccdd4562a498758217)
