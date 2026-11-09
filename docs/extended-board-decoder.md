# Extended board address decoder

This reference pins Ben Eater’s 6502 serial-interface schematic by its image SHA-256. The image has no printed revision identifier. Retrieved 2026-11-09, 3434 × 2202 pixels, SHA-256 `75ef40d1908c1ba0aaf232f07559efe8d4349c5e429ac0ae9521d4f78007420b`. The results below are derived from that drawing and the device data sheets. [Serial schematic](https://eater.net/schematics/6502-serial.png) · [Project page](https://eater.net/6502)

## Physical selects

- U4D inverts A15 to drive the active-low U2 EEPROM chip select. U2's output enable is tied low and write enable is tied high. A0–A14 select the EEPROM byte. The ROM drives the bus whenever selected, including during CPU writes, where it can contend with the CPU. The emulator leaves the ROM unchanged and models no electrical contention for those writes.
- U4C drives U6 SRAM's active-low chip select with `NAND(PHI2, NOT A15)`. SRAM output enable is A14 and write enable is CPU R/W (high on reads). Therefore, during PHI2 high, CPU reads can see SRAM only with A15=A14=0. The SRAM can still accept a physical write anywhere in `$0000–$7FFF`, including I/O addresses, because its write mode does not depend on output enable. The A14=1 half has no CPU read path on this board. The emulator omits those inaccessible SRAM writes. [AS6C62256 truth table](https://www.alliancememory.com/wp-content/uploads/AS6C62256-23-March-2016-rev1.2.pdf)
- U4B generates the common active-low I/O enable `NAND(A14, NOT A15)`. U7 ACIA takes this enable on CS1B and A12 on CS0. Its register offset is A1:A0. U5 VIA takes the same enable on CS2B and A13 on CS1. Its register offset is A3:A0. A12 does not gate the VIA and A13 does not gate the ACIA. Both devices receive PHI2 and CPU R/W. [W65C51N chip and register selects](https://eater.net/datasheets/w65c51n.pdf) · [W65C22S chip and register selects](https://eater.net/datasheets/w65c22.pdf)

This table covers the whole 16-bit address space at the CPU's PHI2-high access phase. A low-phase transition does not constitute an extra CPU transaction. An `x` covers either level.

| A15:A14:A13:A12 | Address range | Physical read driver(s) | Physical write receiver(s) | Default emulator target |
|---|---|---|---|---|
| 0000 | `$0000–$0FFF` | SRAM | SRAM | RAM |
| 0001 | `$1000–$1FFF` | SRAM | SRAM | RAM |
| 0010 | `$2000–$2FFF` | SRAM | SRAM | RAM |
| 0011 | `$3000–$3FFF` | SRAM | SRAM | RAM |
| 0100 | `$4000–$4FFF` | None | Hidden SRAM | Open bus |
| 0101 | `$5000–$5FFF` | ACIA | ACIA and hidden SRAM | ACIA, 4-byte register mirror |
| 0110 | `$6000–$6FFF` | VIA | VIA and hidden SRAM | VIA, 16-byte register mirror |
| 0111 | `$7000–$7FFF` | ACIA and VIA | ACIA, VIA and hidden SRAM | Reserved conflict |
| 1xxx | `$8000–$FFFF` | EEPROM | None, EEPROM output remains enabled | ROM |

The physical I/O selects satisfy `(address & $D000) == $5000` for ACIA and `(address & $E000) == $6000` for VIA. Both are asserted in `$7000–$7FFF`. For the default emulator profile, narrow them to the disjoint, safe portions by matching `$F000` against `$5000` and `$6000`. Keep the canonical symbol bases at `$5000` and `$6000`. Every ACIA register has 1024 decoded addresses including its canonical address, and every VIA register has 256. RAM and ROM are contiguous and use offsets `address & $3FFF` and `address & $7FFF` respectively. The synthetic expanded profile uses its own exact ranges.

## Open bus and conflict policy

- For an unmapped CPU read, return fixed `$FF`. The physical data lines can be undriven and have no guaranteed byte. The fixed value makes execution reproducible regardless of previous bus traffic. An unmapped CPU write is discarded. The default profile models no hidden SRAM side effects.
- For a CPU read or write in the default profile's reserved conflict range, report a bus fault with the address and access direction, and stop execution before invoking a mapped device. Both physical devices can drive a read and both can accept a write. There is no priority winner, returned byte, interrupt acknowledgement, or hidden RAM update in the emulated conflict case.
- A side-effect-free inspection of an unmapped or reserved address reports its classification without fabricating a readable cell or raising a CPU fault. A custom profile must reject intersecting effective mappings, including mirrors. The default profile represents the reserved range explicitly so it cannot fall through to open bus. The reservation belongs to this profile and does not constrain alternative boards.

## Interrupt routing

U7 pin 26 (W65C51N IRQB) and U5 pin 21 (W65C22 IRQB) feed U1 pin 4 through Schottky diodes D1 and D2. Each diode's anode is on the common CPU IRQB net, so an asserting low output can pull that net down. The W65C51N has an open-drain IRQB output. The W65C22S uses a push-pull output and its diode isolates the deasserted high output from another source pulling the common line low. The drawing shows no pull-up on that shared IRQB net. R2 pulls up RDY, while NMI is wired high. Model an ideal pull-up on IRQB and resolve it low if either enabled and connected peripheral asserts its IRQ output. Release it high only once both outputs are inactive. No device in this profile drives NMI. [Serial schematic](https://eater.net/schematics/6502-serial.png) · [W65C51N IRQB](https://eater.net/datasheets/w65c51n.pdf) · [W65C22S IRQB](https://eater.net/datasheets/w65c22.pdf)

| VIA IRQ asserted | ACIA IRQ asserted | CPU IRQB |
|---|---|---|
| No | No | High |
| Yes | No | Low |
| No | Yes | Low |
| Yes | Yes | Low |

## Reference vectors

The default emulator target and offset below are the expected results for M01/M02. For every I/O alias, verify that accesses have the same register side effects as the canonical address, exactly once per CPU access.

| CPU address | Emulator target | Offset |
|---|---|---|
| `$0000` | RAM | `$0000` |
| `$3FFF` | RAM | `$3FFF` |
| `$4000` | Open bus | — |
| `$4FFF` | Open bus | — |
| `$5000` | ACIA | `$0000` |
| `$5003` | ACIA | `$0003` |
| `$5004` | ACIA | `$0000` |
| `$5FFD` | ACIA | `$0001` |
| `$5FFF` | ACIA | `$0003` |
| `$6000` | VIA | `$0000` |
| `$600F` | VIA | `$000F` |
| `$6010` | VIA | `$0000` |
| `$6FED` | VIA | `$000D` |
| `$6FFF` | VIA | `$000F` |
| `$7000` | Reserved conflict | — |
| `$7FFF` | Reserved conflict | — |
| `$8000` | ROM | `$0000` |
| `$FFFA` | ROM | `$7FFA` |
| `$FFFC` | ROM | `$7FFC` |
| `$FFFE` | ROM | `$7FFE` |
| `$FFFF` | ROM | `$7FFF` |

Follow up with these stateful checks in the bus implementation:

- Write `$00` to mapped RAM, read it, then read `$4000` and expect `$FF`. Write a different byte to `$4000` and confirm the read is still `$FF` and mapped RAM was unchanged.
- Attempt both directions in the reserved range with device spies. Expect a fault with the correct address and direction, with no device calls. Inspecting the same address reports the conflict classification without a fault.
- Attempt a CPU write to ROM and verify that the existing byte remains readable.
- Assert both IRQ sources, clear either one, and verify IRQB remains low until the other is cleared.
