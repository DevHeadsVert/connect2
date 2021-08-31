import { Component, OnInit } from '@angular/core';
import {User} from '../model/user';
import {UserService} from '../services/user.service';
import {AuthenticationService} from '../authentication.service';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {

  users: User[];
  page = 1;
  constructor(  
    private authService: AuthenticationService,
    private userService: UserService
                
    ){}

  ngOnInit(): void {
    this.getUsers();
  }

  getUsers(): void {
    this.userService.getUsers().subscribe(users => this.users = users);
  }

  logout(){
    this.authService.logout();
  }
}
