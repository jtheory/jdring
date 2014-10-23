#  jdring

JDRing is a lightweight Java scheduling library that is simple and small, 
but still supports ringing alarms at specified intervals, as one-time events, 
or on complex schedules with full cron support.  
See below for a full list of features.

* **Homepage:**
  [github.com/jtheory/jdring](https://github.com/jtheory/jdring)
  
## Why ?

JDRing was originally written by Olivier Dedieu 
(other contributors: David Sims, Simon Bécot, and Jim Lerner), 
and released under the LGPL license.  
I needed a simple scheduling library, so I fixed some bugs in it and added 
a lot of features that I wanted (including rewriting the scheduling code 
to support full cron flexibility).  
I've tried to send my changes back to Olivier, but he didn't respond, 
and the project homepage has disappeared 
(I think Olivier has moved on to bigger and better things), 
so I'm hosting my version here.

There are many scheduling tools available that you can use in your Java projects.
For large-scale scheduling, workflow management, file monitoring and more 
there are commercial products like Flux (which was actually developed by 
David Sims after his work on JDRing). In comparison, JDRing is quite simple 
and very compact (the jar is only 24K), plus it provides all of the basic 
scheduling features and flexibility that most projects need, 
at no charge and with full source code.

## Features ?

* Alarms are added to and removed from the schedule in code 
  -- no file formats or XML schemas to learn.  
  Just call a method to add an alarm (and specify a listener that you define).

* You can optionally specify a name for each alarm; 
  this lets you use a single listener for multiple alarms.  
  If you don't specify a name, JDRing automatically gives each alarm a unique name.  
 
* Alarms can be rung at a fixed interval (e.g., every 30 minutes), 
  or at a single fixed date (e.g., November 10th, 2004 at 10:15 AM), 
  or on a cron-based flexible schedule.

* With cron-style scheduling, you can provide a list of minutes, hours, 
  days of the month, days of the week, and months (or "all" for any of these),
  and the alarm will ring on every match.  
  For example, you could ring an alarm on every half-hour from 9am through 2pm, 
  Monday through Thursday, in only the summer months.  
  The list would be like this: 
	minutes: 0, 30; 
	hours: 9,10,11,12,13,14; 
	days of week: 1,2,3,4; 
	days of month: all; 
	months: 5,6,7.

* Counting standards are consistent with the java.utils.Calendar class, 
  so minutes range from 0 to 59, hours from 0 to 23, days of the week from
  1 (Sunday) to 7, and days of the month from 1 to 31 (depending on the month).
  Months are not as you might expect, though -- they go from 0 (Jan) to 11 (December).

* When days of week and days of month are both specified in a cron-style schedule, 
  the alarm is rung when either one matches (this is how cron works as well).
  For example, an alarm with 
	minutes: 0; hours: 6; days of week: 2,3; days of month: 1,15; and months: all.  
  This alarm will ring at 6am on every Monday and Tuesday, plus on the first and 
  fifteenth of the month (well, if the first is a Monday, it will ring once on 
  that day).
  
* Alarms by default are rung (listeners are notified) within a single alarm thread, 
  so a long-running alarm will delay following alarms until it completes.  
  This may be what you want -- because alarm tasks may be dependent on previous ones.  
  If not, any alarm may be flagged to ring in a separate thread, so that it will not 
  delay the other alarms.

## license
This program is copyright (c) jtheory creations and others, and licensed as 
open source under the [LGPL (Lesser GNU Public License)](http://www.gnu.org/licenses/lgpl.txt), so you can freely download it,
modify it, and use it.  As long as you obey the limitations of the license 
(read it if you aren't sure!), you can even redistribute it for no charge.
  
## Contributing

1. Fork it
2. Create your feature branch (`git checkout -b my-new-feature`)
3. Commit your changes (`git commit -am 'Add some feature'`)
4. Push to the branch (`git push origin my-new-feature`)
5. Create new Pull Request

