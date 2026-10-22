## A HL7v2 parser

It's a simple HL7v2 parser. The repetitions are not supported, although it works for segments,
fields, components and subcomponents.

### A sample HL7v2 message

```
 MSH|^~\&|GHH LAB|ELAB-3|GHH OE|BLDG4|200202150930||ORU^R01|CNTRL-3456|P|2.4
 PID|||555-44-4444||EVERYWOMAN^EVE^E^^^^L|JONES|19620320|F|||153 FERNWOOD DR.^
 ^STATESVILLE^OH^35292||(206)3345232|(206)752-121||||AC555444444||67-A4335^OH^20030520
 OBR|1|845439^GHH OE|1045813^GHH LAB|15545^GLUCOSE|||200202150730|||||||||
 555-55-5555^PRIMARY^PATRICIA P^^^^MD^^|||||||||F||||||444-44-4444^HIPPOCRATES^HOWARD H^^^^MD
 OBX|1|SN|1554-5^GLUCOSE^POST 12H CFST:MCNC:PT:SER/PLAS:QN||^182|mg/dl|70_105|H|||F
```

The sample message is downloaded from [here](https://www.ringholm.com/docs/04300_en.htm "HL7 Message examples: version 2 and version 3").

### Usage

```java
    String readSystemName() {
        String source = "MSH|^~\\&|SENDER_APP";
        HL7Message message = new HL7Message(source);
        Map<String, List<String>> parts = message.getParts();
        // Just get the first value.
        return parts.get("MSH.3.1").get(0);
    }
```

### HL7v2 Parsing Algorithm

Find any line ending different from the carriage return and replace it with the '\r' character.
Get the first three characters. If it's MSH, FHS or BHS, then update a list of special characters.
The default special characters are | for field, ^ for component, and ~ for repetition.
The ^\~\\\\& value updates the list by adding \\ as the escape character and & for the subcomponent.
There's a special value ^\~&| that does the same as '^~\\&'.

The input HL7v2 message is split into segments. Each segment is separated with the '\r' character.
Empty segments represented with blank lines are omitted. Each segment has to be further split into fields.
The first field of a segment is the first part of the output item name. Those are MSH, PID, NK1 etc.
MSH.2 is a field that contains the special characters used for parsing. In the example message those
are ^~\&.

Each field is split into components. The position of a component is the number of the second
part of the output item name. Each component is split into subcomponents. The position of a subcomponent
is the number of the third part and the last part of the output item name. There's no support for repetitions.
If all values of an output item are empty, it won't be included in the final result.

### A sample output

| Item     | #1 | #2 | #3 | #4 | #5 | #6 |
|----------|:--:|:--:|:--:|:--:|:--:|:--:|
| MSH.1 | | |  |  | 
| MSH.2 | ^~\& |  |  |
| MSH.3.1 | GHH LAB |  |  |
| MSH.4.1 | ELAB-3 |  |  |
| MSH.5.1 | GHH OE |  |  |
| MSH.6.1 | BLDG4 |  |  |
| MSH.7.1 | 200202150930 |  |  |
| MSH.8.1 | ORU |  |  |
| MSH.9 | R01 |  |  |
| MSH.9.1 | CNTRL-3456 |  |  |
| MSH.10.1 | P |  |  |
| MSH.11.1 | 2.4 |  |  |
|  PID.1.1 | 555-44-4444 |  |  |
|  PID.2.1 | EVERYWOMAN |  |  |
|  PID.3 | EVE | E | L |
|  PID.3.1 | JONES |  |  |
|  PID.4.1 | 19620320 |  |  |
|  PID.5.1 | F |  |  |
|  PID.6.1 | 153 FERNWOOD DR. |  |  |
|  ^STATESVILLE^OH^35292.1.1 | (206)3345232 |  |  |
|  ^STATESVILLE^OH^35292.2.1 | (206)752-121 |  |  |
|  ^STATESVILLE^OH^35292.3.1 | AC555444444 |  |  |
|  ^STATESVILLE^OH^35292.4.1 | 67-A4335 |  |  |
|  ^STATESVILLE^OH^35292.5 | OH | 20030520 |  |
|  OBR.1.1 | 1 |  |  |
|  OBR.2.1 | 845439 |  |  |
|  OBR.3 | GHH OE |  |  |
|  OBR.3.1 | 1045813 |  |  |
|  OBR.4 | GHH LAB |  |  |
|  OBR.4.1 | 15545 |  |  |
|  OBR.5 | GLUCOSE |  |  |
|  OBR.5.1 | 200202150730 |  |  |
|  555-55-5555^PRIMARY^PATRICIA P^^^^MD^^.1.1 | F |  |  |
|  555-55-5555^PRIMARY^PATRICIA P^^^^MD^^.2.1 | 444-44-4444 |  |  |
|  555-55-5555^PRIMARY^PATRICIA P^^^^MD^^.3 | HIPPOCRATES | HOWARD H | MD |
|  OBX.1.1 | 1 |  |  |
|  OBX.2.1 | SN |  |  |
|  OBX.3.1 | 1554-5 |  |  |
|  OBX.4 | GLUCOSE | POST 12H CFST:MCNC:PT:SER/PLAS:QN |  |
|  OBX.4.1 | 182 |  |  |
|  OBX.5.1 | mg/dl |  |  |
|  OBX.6.1 | 70_105 |  |  |
|  OBX.7.1 | H |  |  |
|  OBX.8.1 | F |  |  | 