import { Column, Entity, ManyToMany, PrimaryGeneratedColumn } from 'typeorm';
import { Series } from './Series';

@Entity()
export class Category {
  @PrimaryGeneratedColumn()
  id?: number;

  @Column({ nullable: false })
  name: string;

  @ManyToMany(() => Series, series => series.categories)
  serieses: Series[]
}