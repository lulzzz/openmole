/*
 * Copyright (C) 2011 <mathieu.leclaire at openmole.org>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.openmole.ide.core.implementation.preference

import org.openide.util.Lookup
import org.openmole.ide.core.model.factory.IAuthentificationFactoryUI
import org.openmole.ide.misc.widget.MigPanel
import scala.collection.JavaConversions._
import scala.swing.Label
import scala.swing.ScrollPane

class AuthentificationPanel extends MigPanel("wrap","[grow,fill]",""){
  Lookup.getDefault.lookupAll(classOf[IAuthentificationFactoryUI]).foreach(a=>{
      contents+= new Label(a.displayName)
      contents+= new ScrollPane{peer.setViewportView(a.buildPanelUI.peer)}})
  
}
