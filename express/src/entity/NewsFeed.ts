import { Column, Entity, JoinColumn, ManyToOne, PrimaryGeneratedColumn, UpdateDateColumn } from 'typeorm';
import { Series } from './Series';

@Entity()
export class Newsfeed {
  @PrimaryGeneratedColumn()
  id: number;

  @Column({ nullable: false })
  title: string;

  @Column({ type: 'text' })
  description: string;

  @UpdateDateColumn()
  modification: string;

  @ManyToOne(() => Series, series => series.newsfeeds, {
    nullable: false,
    onDelete: 'CASCADE'
  })
  @JoinColumn({ name: "f_series_id" })
  series: Series;
}