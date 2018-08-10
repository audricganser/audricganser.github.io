import { NgModule }             from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { ProjectsComponent }   from './projects/projects.component';
import { ResumeComponent }      from './resume/resume.component';
import { BlogComponent }  from './blog/blog.component';

const routes: Routes = [
  { path: '', redirectTo: '/projects', pathMatch: 'full'},
  { path: 'projects', component: ProjectsComponent},
  { path: 'resume', component: ResumeComponent },
  { path: 'Blog', component: BlogComponent }
];

@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule {}
