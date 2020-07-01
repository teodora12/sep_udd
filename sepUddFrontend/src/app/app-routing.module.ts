import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {FormsModule} from '@angular/forms';
import {CommonModule} from '@angular/common';
import {LoginPageComponent} from './component/login-page/login-page.component';
import {WorkDataPageComponent} from './component/work-data-page/work-data-page.component';



const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'login', component: LoginPageComponent},
  { path: 'workData', component: WorkDataPageComponent}



];

@NgModule({
  imports: [RouterModule.forRoot(routes), FormsModule, CommonModule],
  exports: [RouterModule]
})
export class AppRoutingModule { }
