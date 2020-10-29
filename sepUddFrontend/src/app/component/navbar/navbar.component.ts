import {Component, OnInit} from '@angular/core';
import {UserService} from "../../service/user.service";
import {Router} from "@angular/router";
import {MagazineService} from "../../service/magazine.service";

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  userRole: any;
  user: any;

  constructor(private router: Router, private userService: UserService, private magazineService: MagazineService) {
  }

  ngOnInit() {
    //   this.userRole = this.userService.getLoggedUserType();

    const user = JSON.parse(localStorage.getItem('loggedUser'));
    if (user === null) {
      console.log('user je null');
      this.userRole = '';
    } else {
      console.log('user nije null');
      for (const role of user.roles) {
        if (role.authority === 'ADMIN') {
          this.userRole = 'ROLE_ADMIN';
        } else if (role.authority === 'AUTHOR') {
          this.userRole = 'ROLE_AUTHOR';
        } else if (role.authority === 'MAIN_REDACTOR') {
          this.userRole = 'ROLE_MAINREDACTOR';
        } else if (role.authority === 'USER_ROLE') {
          this.userRole = 'ROLE_USER';
        } else if(role.authority === 'REDACTOR') {
          this.userRole = 'ROLE_REDACTOR';
        }
      }
    }

    console.log(this.userRole);
    const userTemp = JSON.parse(localStorage.getItem('loggedUser'));
    // if (userTemp !== null) {
    //   this.userService.getUserByUsername(userTemp.sub).subscribe(user => {
    //     this.user = user;
    //   });
    // }
  }


  logout() {
//    localStorage.clear();
    localStorage.removeItem('loggedUser');
    this.userRole = '';
//    location.reload();
    this.router.navigate(['/login']);
  }

}
