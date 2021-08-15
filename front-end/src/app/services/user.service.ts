import { Injectable } from '@angular/core';
import { User } from '../model/user';
import { Observable } from 'rxjs';
import {HttpClient, HttpHeaders, HttpResponse} from '@angular/common/http';
import { UserSettings } from '../model/usersettings';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json', Accept: 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) {}

  getUsers(): Observable<User[]> {

    return this.http.get<User[]>('https://localhost:8443/admin/users');
  }

  getUser(id: string): Observable<User> {
    return this.http.get<User>('https://localhost:8443/in/' + id);
  }

  editUserSettings(usersettings: UserSettings): Observable<HttpResponse<any>>{

    return this.http.put<UserSettings>('https://localhost:8443/in/' + usersettings.id.toString() + '/settings', usersettings, {observe : 'response'});

  }
}
