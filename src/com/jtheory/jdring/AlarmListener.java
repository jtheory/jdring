/*
 *  com/jtheory/jdring/AlarmListener.java
 *  Copyright (C) 1999 - 2004 jtheory creations, Olivier Dedieu et al.
 *
 *  This library is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU Library General Public License as published
 *  by the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Library General Public License for more details.
 *
 *  You should have received a copy of the GNU Library General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 */

package com.jtheory.jdring;

import java.util.Date;

/**
  * The listener interface for receiving alarm events.
  *
  * @author  Olivier Dedieu, David Sims, Jim Lerner
  * @version 1.3, 06/15/2000
  */
public interface AlarmListener {
  
  /**
    * Invoked when an alarm is triggered.
    *
    * @param entry the AlarmEntry which has been triggered.
    */
  public abstract void handleAlarm(AlarmEntry entry);
}

